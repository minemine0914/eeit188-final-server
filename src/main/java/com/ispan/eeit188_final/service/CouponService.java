package com.ispan.eeit188_final.service;

import com.ispan.eeit188_final.model.Coupon;
import com.ispan.eeit188_final.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    // Create a new coupon
    public Coupon createCoupon(UUID userId, UUID houseId, float discountRate, int discount, int expire) {
        Coupon coupon = new Coupon();
        coupon.setUserId(userId);
        coupon.setHouseId(houseId);
        coupon.setDiscountRate(discountRate);
        coupon.setDiscount(discount);
        coupon.setExpire(expire);
        coupon.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return couponRepository.save(coupon);
    }

    // Retrieve a coupon by ID
    public Optional<Coupon> getCoupon(UUID id) {
        return couponRepository.findById(id);
    }

    // Retrieve all coupons
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    // Update an existing coupon
    public Optional<Coupon> updateCoupon(UUID id, float discountRate, int discount, int expire) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            coupon.setDiscountRate(discountRate);
            coupon.setDiscount(discount);
            coupon.setExpire(expire);
            return Optional.of(couponRepository.save(coupon));
        }
        return Optional.empty();
    }

    // Delete a coupon by ID
    public void deleteCoupon(UUID id) {
        couponRepository.deleteById(id);
    }

    // Validate if a coupon is still valid
    public boolean isCouponValid(UUID id) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            return now.before(new Timestamp(coupon.getCreatedAt().getTime() + coupon.getExpire() * 1000L));
        }
        return false;
    }
}
