package com.ispan.eeit188_final.service;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.dto.PaymentDTO;
import com.ispan.eeit188_final.dto.TranscationRecordDTO;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.HouseRepository;
import com.ispan.eeit188_final.repository.UserRepository;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class PaymentService {

    @Autowired
    private HouseRepository houseRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransactionRecordService transactionRecordService;

    public String createOrder(PaymentDTO paymentDTO) {
        if (paymentDTO.getUserId() != null && paymentDTO.getHouseId() != null) {
            Optional<House> findHouse = houseRepo.findById(paymentDTO.getHouseId());
            Optional<User> findUser = userRepo.findById(paymentDTO.getUserId());
            if (findHouse.isPresent() && findUser.isPresent()) {
                // 設定本次交易金額 (套用優惠券之類的)
                Integer currentAmount = findHouse.get().getPrice();

                // 建立交易紀錄
                TranscationRecordDTO transcationRecordDTO = TranscationRecordDTO.builder()
                        .houseId(findHouse.get().getId())
                        .userId(findUser.get().getId())
                        .deal("確認付款中")
                        .cashFlow(currentAmount)
                        .platformIncome(0)
                        .build();
                transactionRecordService.create(transcationRecordDTO);

                // 使用 UUID 來生成唯一標識符 (總長度為 20 位)
                String prefix = "EEIT188NOMAD";
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                String uniqueId = prefix + uuid.substring(0, 20 - prefix.length());

                // 實例化 AllInOne 類別
                AllInOne allInOne = new AllInOne("");

                // 建立目前時間 format(yyyy/MM/dd HH:mm:ss) string
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                String timestampAsString = formatter.format(timestamp.toLocalDateTime());

                // 設定 表單資訊
                AioCheckOutALL aio = new AioCheckOutALL();
                aio.setMerchantTradeNo(uniqueId);
                aio.setMerchantTradeDate(timestampAsString);
                aio.setTotalAmount(currentAmount.toString());
                aio.setTradeDesc("Payment for Nomad: 預訂房源[" + findHouse.get().getName() + "]");
                aio.setItemName("Nomad: 預訂房源[" + findHouse.get().getName() + "]");
                aio.setReturnURL("https://yourwebsite.com/return");

                // 呼叫 aioCheckOut 方法，傳入 aio 物件
                String ecpayPostForm = allInOne.aioCheckOut(aio, null);

                // 自動 Submit HTML
                String autoSubmitForm = "<html>" +
                    "<body onload='document.forms[\"ecpayForm\"].submit()'>" +
                    ecpayPostForm +
                    "</body>" +
                    "</html>";
                
                return autoSubmitForm;
            }
        }
        return null;
    }
}
