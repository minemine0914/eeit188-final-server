package com.ispan.eeit188_final.service;

import com.ispan.eeit188_final.dto.CouponDTO;
import com.ispan.eeit188_final.model.Coupon;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.CouponRepository;
import com.ispan.eeit188_final.repository.HouseRepository;
import com.ispan.eeit188_final.repository.UserRepository;
import com.ispan.eeit188_final.repository.specification.CouponSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CouponService {

    // 預設值
    private static final Integer PAGEABLE_DEFAULT_PAGE = 0;
    private static final Integer PAGEABLE_DEFAULT_LIMIT = 100;

    @Autowired
    private CouponRepository couponRepo;

    @Autowired
    private HouseRepository houseRepo;

    @Autowired
    private UserRepository userRepo;

    // Create a new transaction record
    public Coupon create(CouponDTO dto) {
        // if (dto.getUserId() != null && dto.getHouseId() != null) {
        if (dto.getUserId() != null) {
            // Optional<House> findHouse = houseRepo.findById(dto.getHouseId());
            Optional<User> findUser = userRepo.findById(dto.getUserId());
            // if (findHouse.isPresent() && findUser.isPresent()) {
            if (findUser.isPresent()) {
                Coupon create = Coupon.builder()
                        // .house(findHouse.get())
                        .user(findUser.get())
                        .discountRate(dto.getDiscountRate())
                        .discount(dto.getDiscount())
                        .expire(dto.getExpire())
                        .name(dto.getName())
                        .build();
                return couponRepo.save(create);
            }
        }
        return null;
    }

    // public Coupon modify(UUID id, CouponDTO dto) {
    // if (id != null) {
    // Optional<Coupon> find = couponRepo.findById(id);
    // if (find.isPresent()) {
    // Coupon modify = find.get();
    // }
    // }
    // return null;
    // }

    public Boolean delete(UUID id) {
        if (id != null) {
            Optional<Coupon> find = couponRepo.findById(id);
            if (find.isPresent()) {
                couponRepo.deleteById(id);
                return true;
            }
        }
        return false;
    }

    public Coupon findById(UUID id) {
        if (id != null) {
            Optional<Coupon> find = couponRepo.findById(id);
            if (find.isPresent()) {
                return find.get();
            }
        }
        return null;
    }

    public Page<Coupon> findAll(CouponDTO dto) {
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(dto.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
        Integer limit = Optional.ofNullable(dto.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
        Boolean dir = Optional.ofNullable(dto.getDir()).orElse(false);
        String order = Optional.ofNullable(dto.getOrder()).orElse(null);
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return couponRepo.findAll(PageRequest.of(page, limit, sort));
    }

    // 條件查詢
    public Page<Coupon> find(CouponDTO dto) {
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(dto.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
        Integer limit = Optional.ofNullable(dto.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
        Boolean dir = Optional.ofNullable(dto.getDir()).orElse(false);
        String order = Optional.ofNullable(dto.getOrder()).orElse(null);
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return couponRepo.findAll(CouponSpecification.filterCoupon(dto), PageRequest.of(page, limit, sort));
    }

    public Page<Coupon> findAllDiscountRate(CouponDTO dto) {
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(dto.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
        Integer limit = Optional.ofNullable(dto.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
        Boolean dir = Optional.ofNullable(dto.getDir()).orElse(false);
        String order = Optional.ofNullable(dto.getOrder()).orElse(null);
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return couponRepo.findByDiscountIsNull(PageRequest.of(page, limit, sort));
    }

    public Page<Coupon> findAllDiscount(CouponDTO dto) {
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(dto.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
        Integer limit = Optional.ofNullable(dto.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
        Boolean dir = Optional.ofNullable(dto.getDir()).orElse(false);
        String order = Optional.ofNullable(dto.getOrder()).orElse(null);
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return couponRepo.findByDiscountRateIsNull(PageRequest.of(page, limit, sort));
    }

    public Page<Coupon> findDiscountRate(CouponDTO dto) {
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(dto.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
        Integer limit = Optional.ofNullable(dto.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
        Boolean dir = Optional.ofNullable(dto.getDir()).orElse(false);
        String order = Optional.ofNullable(dto.getOrder()).orElse(null);
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return couponRepo.findByDiscountIsNull(CouponSpecification.filterCoupon(dto),
                PageRequest.of(page, limit, sort));
    }

    public Page<Coupon> findDiscount(CouponDTO dto) {
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(dto.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
        Integer limit = Optional.ofNullable(dto.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
        Boolean dir = Optional.ofNullable(dto.getDir()).orElse(false);
        String order = Optional.ofNullable(dto.getOrder()).orElse(null);
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return couponRepo.findByDiscountRateIsNull(CouponSpecification.filterCoupon(dto),
                PageRequest.of(page, limit, sort));
    }

    // 為所有使用者新增優惠券
    public void createCouponForAllUsers(CouponDTO couponDTO) {
        int pageSize = 100; // 每次處理 100 筆資料
        int pageNumber = 0;
        Page<User> usersPage;

        // 持續查詢分頁的使用者，直到查詢完所有頁面
        do {

            usersPage = userRepo.findAll(PageRequest.of(pageNumber, pageSize));

            // 為分頁中的每個使用者新增優惠券
            for (User user : usersPage.getContent()) {
                Coupon coupon = new Coupon();
                coupon.setDiscountRate(couponDTO.getDiscountRate());
                coupon.setDiscount(couponDTO.getDiscount());
                coupon.setExpire(couponDTO.getExpire());
                coupon.setName(couponDTO.getName());
                coupon.setUser(user);

                // 儲存優惠券
                couponRepo.save(coupon);
            }

            // 繼續處理下一頁
            pageNumber++;
        } while (usersPage.hasNext());
    }

}
