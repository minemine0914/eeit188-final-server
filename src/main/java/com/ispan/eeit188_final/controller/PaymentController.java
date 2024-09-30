package com.ispan.eeit188_final.controller;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.eeit188_final.dto.PaymentDTO;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.service.HouseService;
import com.ispan.eeit188_final.service.PaymentService;
import com.ispan.eeit188_final.service.TicketService;

@CrossOrigin
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private HouseService houseService;

    @PostMapping("/booking-house")
    public ResponseEntity<String> bookingHouse(@RequestBody PaymentDTO paymentDTO) {
        try {
            // DTO params: houseId, userId, dateRange[], (couponId)
            String autoSubmitForm = paymentService.createOrder(paymentDTO);

            if (autoSubmitForm != null) {
                return ResponseEntity.ok(autoSubmitForm);
            } else {
                // 返回詳細的錯誤信息
                return ResponseEntity.badRequest().body("Failed to create order.");
            }
        } catch (IllegalArgumentException e) {
            // 處理不合法的參數錯誤
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            // 處理找不到使用者或房屋的錯誤
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            // 處理房屋不可用的錯誤
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // 處理其他異常
            e.printStackTrace(); // 紀錄錯誤日誌
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PostMapping("/house-available")
    public ResponseEntity<?> postMethodName(@RequestBody PaymentDTO paymentDTO) {
        if (paymentDTO.getDateRange() == null || paymentDTO.getHouseId() == null) {
            return ResponseEntity.badRequest().body("HouseId or dateRange can not be null");
        }

        if (paymentDTO.getDateRange().length < 1) {
            return ResponseEntity.badRequest().body("dateRange must have 'start' and 'end' of two array");
        }

        if (paymentDTO.getDateRange()[0] == null || paymentDTO.getDateRange()[1] == null) {
            return ResponseEntity.badRequest().body("dateRange start or end date is null");
        }

        House findHouse = houseService.findById(paymentDTO.getHouseId());
        if (findHouse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("House not found");
        }

        Boolean houseAvailable = ticketService.isHouseAvailable(findHouse, paymentDTO.getDateRange()[0],
                paymentDTO.getDateRange()[1]);

        if (houseAvailable) {
            return ResponseEntity.ok("House is available");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("入住期間已被其他人預定");
        }
    }

}
