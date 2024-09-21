package com.ispan.eeit188_final.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.eeit188_final.dto.PaymentDTO;
import com.ispan.eeit188_final.service.PaymentService;

import ecpay.payment.integration.domain.AioCheckOutALL;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody PaymentDTO paymentDTO) {
        AioCheckOutALL aio = new AioCheckOutALL();

        aio.setMerchantTradeNo(paymentDTO.getTradeNo());
        aio.setMerchantTradeDate(paymentDTO.getTradeDate());
        aio.setTotalAmount(paymentDTO.getTotalAmount());
        aio.setTradeDesc(paymentDTO.getTradeDesc());
        aio.setItemName(paymentDTO.getItemName());
        aio.setReturnURL("https://yourwebsite.com/return");

        String htmlForm = paymentService.createOrder(aio);
        return ResponseEntity.ok(htmlForm);
    }
}
