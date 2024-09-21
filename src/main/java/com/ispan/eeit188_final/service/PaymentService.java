package com.ispan.eeit188_final.service;

import org.springframework.stereotype.Service;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class PaymentService {
    public String createOrder(AioCheckOutALL aio) {
        // 實例化 AllInOne 類別
        AllInOne allInOne = new AllInOne("");

        // 呼叫 aioCheckOut 方法，傳入 aio 物件
        String htmlForm = allInOne.aioCheckOut(aio, null);

        // 回傳產生的 HTML 表單 (之後可以在 Controller 層返回給前端)
        return htmlForm;
    }
}
