package com.ispan.eeit188_final.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.dto.PriceRangeDTO;
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

    public Page<PriceRange> findAll(PriceRangeDTO priceRangeDTO) {
        // 預設 頁數 限制
        Integer defaultPage = 0;
        Integer defaultLimit = 10;
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(priceRangeDTO.getPage()).orElse(defaultPage);
        Integer limit = Optional.ofNullable(priceRangeDTO.getLimit()).orElse(defaultLimit);
        Boolean dir = Optional.ofNullable(priceRangeDTO.getDir()).orElse(false);
        String order = Optional.ofNullable(priceRangeDTO.getOrder()).orElse(null);
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return priceRangeRepo.findAll(PageRequest.of(page, limit, sort));
    }

    public Page<PriceRange> find(PriceRangeDTO priceRangeDTO) {
        // 預設 頁數 限制
        Integer defaultPage = 0;
        Integer defaultLimit = 10;
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(priceRangeDTO.getPage()).orElse(defaultPage);
        Integer limit = Optional.ofNullable(priceRangeDTO.getLimit()).orElse(defaultLimit);
        Boolean dir = Optional.ofNullable(priceRangeDTO.getDir()).orElse(false);
        String order = Optional.ofNullable(priceRangeDTO.getOrder()).orElse(null);
        // 條件查詢
        Specification<PriceRange> spec = Specification.where(null);
        spec = spec.and(PriceRangeSpecification.filterPriceRange(priceRangeDTO));
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return priceRangeRepo.findAll(spec, PageRequest.of(page, limit, sort));
    }

}
