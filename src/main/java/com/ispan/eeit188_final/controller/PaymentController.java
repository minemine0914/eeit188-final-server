package com.ispan.eeit188_final.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.eeit188_final.dto.PaymentDTO;
import com.ispan.eeit188_final.service.PaymentService;

import ecpay.payment.integration.domain.AioCheckOutALL;

@CrossOrigin
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/booking-house")
    public ResponseEntity<String> bookingHouse(@RequestBody PaymentDTO paymentDTO) {
        // DTO params: houseId, userId
        String autoSubmitForm = paymentService.createOrder(paymentDTO);

        if (autoSubmitForm != null) {
            return ResponseEntity.ok(autoSubmitForm);
        }
        
        return ResponseEntity.badRequest().build();
    }

}
