package com.ispan.eeit188_final.controller;

import com.ispan.eeit188_final.model.Coupon;
import com.ispan.eeit188_final.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping
    public ResponseEntity<String> createCoupon(@RequestBody String jsonRequest) {
        return couponService.createCoupon(jsonRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCoupon(@PathVariable UUID id) {
        return couponService.getCoupon(id);
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Coupon>> getCouponsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(couponService.getCouponsByUserId(userId));
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<Coupon>> getCouponsByHouseId(@PathVariable UUID houseId) {
        return ResponseEntity.ok(couponService.getCouponsByHouseId(houseId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCoupon(@PathVariable UUID id, @RequestBody String jsonRequest) {
        return couponService.updateCoupon(id, jsonRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable UUID id) {
        return couponService.deleteCoupon(id);
    }
}