package com.ispan.eeit188_final.service;

import com.ispan.eeit188_final.model.Coupon;
import com.ispan.eeit188_final.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public ResponseEntity<String> createCoupon(String jsonRequest) {
        if (jsonRequest != null && !jsonRequest.isEmpty()) {
            try {
                JSONObject obj = new JSONObject(jsonRequest);

                UUID userId = obj.isNull("userId") ? null : UUID.fromString(obj.getString("userId"));
                UUID houseId = obj.isNull("houseId") ? null : UUID.fromString(obj.getString("houseId"));
                float discountRate = obj.isNull("discountRate") ? 0 : (float) obj.getDouble("discountRate");
                int discount = obj.isNull("discount") ? 0 : obj.getInt("discount");
                int expire = obj.isNull("expire") ? 0 : obj.getInt("expire");

                if (userId == null) {
                    return ResponseEntity.badRequest().body("{\"message\": \"User ID can't be null\"}");
                }

                if (houseId == null) {
                    return ResponseEntity.badRequest().body("{\"message\": \"House ID can't be null\"}");
                }

                Coupon coupon = new Coupon();
                coupon.setUserId(userId);
                coupon.setHouseId(houseId);
                coupon.setDiscountRate(discountRate);
                coupon.setDiscount(discount);
                coupon.setExpire(expire);
                coupon.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                couponRepository.save(coupon);

                return ResponseEntity.ok("{\"message\": \"Successfully created coupon\"}");
            } catch (JSONException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid JSON request\"}");
    }

    public ResponseEntity<String> getCoupon(UUID id) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<Coupon> optional = couponRepository.findById(id);

            if (optional.isPresent()) {
                Coupon coupon = optional.get();

                try {
                    JSONObject obj = new JSONObject()
                            .put("userId", coupon.getUserId())
                            .put("houseId", coupon.getHouseId())
                            .put("discountRate", coupon.getDiscountRate())
                            .put("discount", coupon.getDiscount())
                            .put("expire", coupon.getExpire())
                            .put("createdAt", coupon.getCreatedAt());

                    return ResponseEntity.ok(obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error creating JSON: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"Coupon not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public List<Coupon> getCouponsByUserId(UUID userId) {
        return couponRepository.findAll().stream()
                .filter(coupon -> coupon.getUserId().equals(userId))
                .toList();
    }

    public List<Coupon> getCouponsByHouseId(UUID houseId) {
        return couponRepository.findAll().stream()
                .filter(coupon -> coupon.getHouseId().equals(houseId))
                .toList();
    }

    public ResponseEntity<String> updateCoupon(UUID id, String jsonRequest) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<Coupon> optional = couponRepository.findById(id);

            if (optional.isPresent()) {
                Coupon coupon = optional.get();

                try {
                    JSONObject obj = new JSONObject(jsonRequest);

                    float discountRate = obj.isNull("discountRate") ? 0 : (float) obj.getDouble("discountRate");
                    int discount = obj.isNull("discount") ? 0 : obj.getInt("discount");
                    int expire = obj.isNull("expire") ? 0 : obj.getInt("expire");

                    coupon.setDiscountRate(discountRate);
                    coupon.setDiscount(discount);
                    coupon.setExpire(expire);

                    couponRepository.save(coupon);

                    return ResponseEntity.ok("{\"message\": \"Successfully updated coupon\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"Coupon not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }

    public ResponseEntity<String> deleteCoupon(UUID id) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<Coupon> optional = couponRepository.findById(id);

            if (optional.isPresent()) {
                couponRepository.deleteById(id);
                return ResponseEntity.ok("{\"message\": \"Coupon deleted successfully\"}");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"Coupon not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }
}
