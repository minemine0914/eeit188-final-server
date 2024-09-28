package com.ispan.eeit188_final.service;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.dto.PaymentDTO;
import com.ispan.eeit188_final.dto.TicketDTO;
import com.ispan.eeit188_final.dto.TranscationRecordDTO;
import com.ispan.eeit188_final.model.Coupon;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.Ticket;
import com.ispan.eeit188_final.model.TransactionRecord;
import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.CouponRepository;
import com.ispan.eeit188_final.repository.HouseRepository;
import com.ispan.eeit188_final.repository.UserRepository;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class PaymentService {

    private static final String HTML_FAILED_STRING = "<html>\n" +
            "    <head>\n" +
            "        <meta charset=\"UTF-8\">\n" +
            "        <title>NOMAD支付</title>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <div style=\"text-align: center; width: 100%;\">\n" +
            "            <p>交易失敗，請關閉視窗重新預定房源!</p>\n" +
            "            <button onclick=\"window.close();\">關閉視窗</button>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>";

    @Value("${eeit188final.payment.platform-commission}")
    private Integer platformCommission; // 0 ~ 100 (%)

    @Autowired
    private HouseRepository houseRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CouponRepository couponRepo;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TransactionRecordService transactionRecordService;

    public String createOrder(PaymentDTO paymentDTO) {
        try {
            // 檢查 User 和 House ID 是否有效
            if (paymentDTO.getUserId() == null || paymentDTO.getHouseId() == null) {
                throw new IllegalArgumentException("Invalid User or House ID.");
            }

            Optional<House> findHouse = houseRepo.findById(paymentDTO.getHouseId());
            Optional<User> findUser = userRepo.findById(paymentDTO.getUserId());

            if (!findHouse.isPresent()) {
                throw new NoSuchElementException("House not found.");
            }

            if (!findUser.isPresent()) {
                throw new NoSuchElementException("User not found.");
            }

            // 確認日期範圍是否有效
            Timestamp[] dateRange = paymentDTO.getDateRange();
            if (dateRange == null || dateRange.length != 2) {
                throw new IllegalArgumentException("Invalid date range.");
            }

            Timestamp start = dateRange[0];
            Timestamp end = dateRange[1];

            // 確認開始日期必須早於結束日期
            if (start.after(end)) {
                throw new IllegalArgumentException("Start date must be earlier than end date.");
            }

            // 檢查房屋在指定日期範圍內是否可用
            Boolean isAvailable = ticketService.isHouseAvailable(findHouse.get(), start, end);
            if (!isAvailable) {
                throw new IllegalStateException("The house is not available for the selected date range.");
            }

            // 計算訂房的金額和天數
            long daysBetween = ChronoUnit.DAYS.between(start.toLocalDateTime(), end.toLocalDateTime());
            if (daysBetween <= 0) {
                throw new IllegalArgumentException("Invalid booking duration.");
            }

            Integer currentAmount = findHouse.get().getPrice() * (int) daysBetween;

            // 應用優惠券折扣
            if (paymentDTO.getCouponId() != null) {
                Optional<Coupon> findCoupon = couponRepo.findById(paymentDTO.getCouponId());
                if (findCoupon.isPresent()) {
                    Coupon coupon = findCoupon.get();
                    currentAmount -= coupon.getDiscount(); // 應用折扣
                }
            }

            // 計算平台抽成
            Integer platformIncome = (int) (currentAmount * platformCommission / 100);
            Integer finalAmount = currentAmount - platformIncome;

            // 建立 交易紀錄
            TranscationRecordDTO transcationRecordDTO = createTransactionRecord(findHouse.get(), findUser.get(),
                    finalAmount, platformIncome);
            TransactionRecord transactionRecord = transactionRecordService.create(transcationRecordDTO);

            // 建立 票券
            TicketDTO ticketDTO = createTicket(findHouse.get(), findUser.get(), transactionRecord, start, end);
            Ticket ticket = ticketService.create(ticketDTO);

            // 檢查是否成功創建交易和票券
            if (transactionRecord == null || ticket == null) {
                throw new IllegalStateException("Failed to create transaction or ticket.");
            }

            // 使用 UUID 生成唯一的訂單號
            String uniqueId = generateUniqueTradeNo();

            // 調用綠界支付 API
            String ecpayPostForm = generateECPayForm(uniqueId, currentAmount, findHouse.get().getName());

            // 返回自動提交表單的 HTML
            return createAutoSubmitForm(ecpayPostForm);
        } catch (IllegalArgumentException | NoSuchElementException | IllegalStateException e) {
            // 捕捉已知錯誤並返回錯誤信息
            return e.getMessage();
        } catch (Exception e) {
            // 捕捉其他非預期錯誤
            e.printStackTrace();
            return "An error occurred during the order creation.";
        }
    }

    // 設置交易紀錄
    private TranscationRecordDTO createTransactionRecord(House house, User user, Integer cashFlow,
            Integer platformIncome) {
        return TranscationRecordDTO.builder()
                .houseId(house.getId())
                .userId(user.getId())
                .deal("確認付款中")
                .cashFlow(cashFlow)
                .platformIncome(platformIncome)
                .build();
    }

    // 設置票券
    private TicketDTO createTicket(House house, User user, TransactionRecord transactionRecord, Timestamp start,
            Timestamp end) {
        return TicketDTO.builder()
                .startedAt(start)
                .endedAt(end)
                .houseId(house.getId())
                .userId(user.getId())
                .transactionRecordId(transactionRecord.getId())
                .used(false)
                .review(false)
                .build();
    }

    // 生成唯一的訂單號
    private String generateUniqueTradeNo() {
        String prefix = "EEIT188NOMAD";
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return prefix + uuid.substring(0, 20 - prefix.length());
    }

    // 生成綠界支付表單
    private String generateECPayForm(String tradeNo, Integer amount, String houseName) {
        AllInOne allInOne = new AllInOne("");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String timestampAsString = formatter.format(timestamp.toLocalDateTime());

        AioCheckOutALL aio = new AioCheckOutALL();
        aio.setMerchantTradeNo(tradeNo);
        aio.setMerchantTradeDate(timestampAsString);
        aio.setTotalAmount(amount.toString());
        aio.setTradeDesc("Payment for Nomad: 預訂房源[" + houseName + "]");
        aio.setItemName("Nomad: 預訂房源[" + houseName + "]");
        aio.setReturnURL("https://yourwebsite.com/return");

        return allInOne.aioCheckOut(aio, null);
    }

    // 生成自動提交表單的 HTML
    private String createAutoSubmitForm(String ecpayPostForm) {
        return "<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>NOMAD支付</title>" +
                "</head>" +
                "<body onload='document.forms[\"ecpayForm\"].submit()'>" +
                "<div style=\"text-align: center; width: 100%;\">" +
                "<p>即將跳轉至綠界支付，請稍候...</p>" +
                "</div>" +
                ecpayPostForm +
                "</body>" +
                "</html>";
    }

}
