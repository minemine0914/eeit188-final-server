package com.ispan.eeit188_final.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;
import java.sql.Timestamp;
import java.util.List;
import com.ispan.eeit188_final.dto.CouponDTO;
import com.ispan.eeit188_final.model.Coupon;
import com.ispan.eeit188_final.model.House;

import jakarta.persistence.criteria.Expression;

public class CouponSpecification {
    // 根據 houseId 查詢
    // public static Specification<Coupon> filterByHouseId(UUID houseId) {
    //     return (root, query, criteriaBuilder) -> {
    //         if (houseId != null) {
    //             return criteriaBuilder.equal(root.get("house").get("id"), houseId);
    //         }
    //         return null;
    //     };
    // }

    // 根據 userId 查詢
    public static Specification<Coupon> filterByUserId(UUID userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId != null) {
                return criteriaBuilder.equal(root.get("user").get("id"), userId);
            }
            return null;
        };
    }

    // 使用者名稱
    public static Specification<Coupon> hasUserName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction(); // 如果 name 為 null 或空字串，返回無條件過濾
            }
            return cb.like(root.get("user").get("name"), "%" + name + "%"); // 使用 % 符號進行模糊搜尋
        };
    }

    // 根據 discountRate 查詢
    public static Specification<Coupon> filterByDiscountRate(Double discountRate) {
        return (root, query, criteriaBuilder) -> {
            if (discountRate != null) {
                return criteriaBuilder.equal(root.get("discountRate"), discountRate);
            }
            return null;
        };
    }

    // 根據 discount 查詢
    public static Specification<Coupon> filterByDiscount(Integer discount) {
        return (root, query, criteriaBuilder) -> {
            if (discount != null) {
                return criteriaBuilder.equal(root.get("discount"), discount);
            }
            return null;
        };
    }

    // 根據 expire 查詢
    public static Specification<Coupon> filterByExpire(Integer expire) {
        return (root, query, criteriaBuilder) -> {
            if (expire != null) {
                return criteriaBuilder.equal(root.get("expire"), expire);
            }
            return null;
        };
    }

    // 優惠券名稱
    public static Specification<Coupon> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction(); // 如果 name 為 null 或空字串，返回無條件過濾
            }
            return cb.like(root.get("name"), "%" + name + "%"); // 使用 % 符號進行模糊搜尋
        };
    }

    // 根據是否過期查詢
    public static Specification<Coupon> filterByExpirationStatus(boolean isExpired) {
        return (root, query, criteriaBuilder) -> {
            Expression<Timestamp> expirationTime = criteriaBuilder.function(
                "DATEADD", Timestamp.class, 
                criteriaBuilder.literal("DAY"), root.get("expire"), root.get("createdAt")
            );
            if (isExpired) {
                // 查詢已過期的優惠券
                return criteriaBuilder.lessThanOrEqualTo(expirationTime, new Timestamp(System.currentTimeMillis()));
            } else {
                // 查詢未過期的優惠券
                return criteriaBuilder.greaterThan(expirationTime, new Timestamp(System.currentTimeMillis()));
            }
        };
    }

    // 組合多條件查詢
    public static Specification<Coupon> filterCoupon(CouponDTO dto) {
        Specification<Coupon> spec = Specification.where(null);

        // // 添加 houseId 查詢條件
        // if (dto.getHouseId() != null) {
        //     spec = spec.and(filterByHouseId(dto.getHouseId()));
        // }

        // 添加 userId 查詢條件
        if (dto.getUserId() != null) {
            spec = spec.and(filterByUserId(dto.getUserId()));
        }

        // 添加 userName 查詢條件
        if (dto.getUserName() != null) {
            spec = spec.and(hasUserName(dto.getUserName()));
        }
        // 添加 name 查詢條件
        if (dto.getName() != null) {
            spec = spec.and(hasName(dto.getName()));
        }

        // 添加 discountRate 查詢條件
        if (dto.getDiscountRate() != null) {
            spec = spec.and(filterByDiscountRate(dto.getDiscountRate()));
        }

        // 添加 discount 查詢條件
        if (dto.getDiscount() != null) {
            spec = spec.and(filterByDiscount(dto.getDiscount()));
        }

        // 添加 expire 查詢條件
        if (dto.getExpire() != null) {
            spec = spec.and(filterByExpire(dto.getExpire()));
        }

        // 添加是否過期查詢條件
        if (dto.getIsExpired() != null) {
            spec = spec.and(filterByExpirationStatus(dto.getIsExpired()));
        }

        return spec;
    }
}
