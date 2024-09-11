package com.ispan.eeit188_final.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ispan.eeit188_final.dto.CouponDTO;
import com.ispan.eeit188_final.model.Coupon;
import com.ispan.eeit188_final.service.CouponService;

@CrossOrigin
@RestController
@RequestMapping("/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    /** 新增一筆 */
    @PostMapping("/")
    public ResponseEntity<Coupon> create(@RequestBody CouponDTO dto) {
        Coupon created = couponService.create(dto);
        if (created != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(created.getId())
                    .toUri();
            return ResponseEntity.created(location).body(created); // Return 201 Created and redirect to created url
        }
        return ResponseEntity.badRequest().build(); // Return 400 BadRequest
    }

    // 查詢一筆 id
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> findById(@PathVariable UUID id) {
        Coupon find = couponService.findById(id);
        if (find != null) {
            return ResponseEntity.ok(find); // Return 200
        }
        return ResponseEntity.notFound().build(); // Return 404 NotFound
    }

    // 刪除一筆 id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        Boolean deleted = couponService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Return 204 NoContent
        }
        return ResponseEntity.notFound().build(); // Return 400 NotFound
    }

    /** 查詢所有 */
    @GetMapping("/all")
    public Page<Coupon> all(@ModelAttribute CouponDTO dto) {
        return couponService.findAll(dto);
    }

    /** 條件查詢 */
    @PostMapping("/search")
    public Page<Coupon> search(@RequestBody CouponDTO dto) {
        return couponService.find(dto);
    }
}
