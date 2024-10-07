package com.ispan.eeit188_final.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.eeit188_final.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID>, JpaSpecificationExecutor<Coupon> {

    // 定義查詢 discountRate 為 NULL 的方法
    Page<Coupon> findByDiscountRateIsNull(Pageable pageable);
    // 定義查詢 discountRate 為 NULL 的方法
    Page<Coupon> findByDiscountRateIsNull(Specification<Coupon> specification ,Pageable pageable);

    // 定義查詢 discountRate 為 NULL 的方法
    Page<Coupon> findByDiscountIsNull(Pageable pageable);
    // 定義查詢 discountRate 為 NULL 的方法
    Page<Coupon> findByDiscountIsNull(Specification<Coupon> specification ,Pageable pageable);
}
