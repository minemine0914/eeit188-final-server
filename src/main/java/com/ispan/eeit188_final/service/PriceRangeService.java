package com.ispan.eeit188_final.service;

import java.util.Optional;
import java.util.UUID;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.PriceRange;
import com.ispan.eeit188_final.repository.PriceRangeRepository;
import com.ispan.eeit188_final.repository.specification.PriceRangeSpecification;

@Service
public class PriceRangeService {
    @Autowired
    private PriceRangeRepository priceRangeRepo;

    public PriceRange create(PriceRange priceRange) {
        return priceRangeRepo.save(priceRange);
    }

    public PriceRange modify(UUID id, PriceRange priceRange) {
        if (id != null) {
            Optional<PriceRange> find = priceRangeRepo.findById(id);
            if (find.isPresent()) {
                PriceRange modify = find.get();
                Optional.ofNullable(priceRange.getNewPrice()).ifPresent(modify::setNewPrice); // 價錢
                Optional.ofNullable(priceRange.getStartedAt()).ifPresent(modify::setStartedAt); // 起始時間
                Optional.ofNullable(priceRange.getEndedAt()).ifPresent(modify::setEndedAt); // 結束時間
                return priceRangeRepo.save(modify);
            }
        }
        return null;
    }

    public Boolean delete(UUID id) {
        if (id != null) {
            Optional<PriceRange> find = priceRangeRepo.findById(id);
            if (find.isPresent()) {
                priceRangeRepo.deleteById(id);
                return true;
            }
        }
        return false;
    }

    public PriceRange findById(UUID id) {
        if (id != null) {
            Optional<PriceRange> find = priceRangeRepo.findById(id);
            if (find.isPresent()) {
                return find.get();
            }
        }
        return null;
    }

    public Page<PriceRange> findAll(String jsonString) {
        /*
         * JSON keys: page, limit, dir, order
         */
        JSONObject obj = new JSONObject(jsonString);
        Integer defaultPage = 0;
        Integer defaultLimit = 10;
        Integer page = obj.isNull("page") ? defaultPage : obj.getInt("page");
        Integer limit = obj.isNull("limit") ? defaultLimit : obj.getInt("limit");
        Boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
        String order = obj.isNull("order") ? null : obj.getString("order");
        if (order != null) {
            return priceRangeRepo
                    .findAll(PageRequest.of(page, limit, Sort.by(dir ? Direction.DESC : Direction.ASC, order)));
        }
        return priceRangeRepo.findAll(PageRequest.of(page, limit, Sort.by(dir ? Direction.DESC : Direction.ASC)));
    }

    public Page<PriceRange> find(String jsonString) {
        // 預設 頁數 限制
        Integer defaultPage = 0;
        Integer defaultLimit = 10;
        JSONObject obj = new JSONObject(jsonString);
        // 頁數 限制 排序
        Integer page = obj.isNull("page") ? defaultPage : obj.getInt("page");
        Integer limit = obj.isNull("limit") ? defaultLimit : obj.getInt("limit");
        Boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
        String order = obj.isNull("order") ? "id" : obj.getString("order");
        // 條件查詢
        Specification<PriceRange> spec = Specification.where(null);
        spec = spec.and(PriceRangeSpecification.filterPriceRange(jsonString));
        if (order != null) {
            return priceRangeRepo.findAll(spec,
                    PageRequest.of(page, limit, Sort.by(dir ? Direction.DESC : Direction.ASC, order)));
        }
        return priceRangeRepo.findAll(spec, PageRequest.of(page, limit, Sort.by(dir ? Direction.DESC : Direction.ASC)));
    }

}
