package com.ispan.eeit188_final.repository.specification;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.ispan.eeit188_final.dto.TranscationRecordDTO;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.TransactionRecord;

public class TranscationRecordSpecification {

    // 根據 houseId 查詢
    public static Specification<TransactionRecord> filterByHouseId(UUID houseId) {
        return (root, query, criteriaBuilder) -> {
            if (houseId != null) {
                return criteriaBuilder.equal(root.get("house").get("id"), houseId);
            }
            return null;
        };
    }

    // 根據 userId 查詢
    public static Specification<TransactionRecord> filterByUserId(UUID userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId != null) {
                return criteriaBuilder.equal(root.get("userId"), userId);
            }
            return null;
        };
    }

    // 根據 cashFlow 查詢
    public static Specification<TransactionRecord> filterByCashFlow(Integer cashFlow) {
        return (root, query, criteriaBuilder) -> {
            if (cashFlow != null) {
                return criteriaBuilder.equal(root.get("cashFlow"), cashFlow);
            }
            return null;
        };
    }

    // 根據 deal 查詢
    public static Specification<TransactionRecord> filterByDeal(String deal) {
        return (root, query, criteriaBuilder) -> {
            if (deal != null && !deal.isEmpty()) {
                return criteriaBuilder.equal(root.get("deal"), deal);
            }
            return null;
        };
    }

    // 根據 platformIncome 查詢
    public static Specification<TransactionRecord> filterByPlatformIncome(Integer platformIncome) {
        return (root, query, criteriaBuilder) -> {
            if (platformIncome != null) {
                return criteriaBuilder.equal(root.get("platformIncome"), platformIncome);
            }
            return null;
        };
    }

    // 根據 CreatedAt 範圍查詢
    public static Specification<TransactionRecord> hasCreatedAtBetween(Timestamp minCreatedAt, Timestamp maxCreatedAt) {
        return (root, query, cb) -> {
            if (minCreatedAt == null && maxCreatedAt == null) {
                return cb.conjunction();
            } else if (minCreatedAt == null) {
                return cb.lessThanOrEqualTo(root.get("createdAt"), maxCreatedAt);
            } else if (maxCreatedAt == null) {
                return cb.greaterThanOrEqualTo(root.get("createdAt"), minCreatedAt);
            } else {
                return cb.between(root.get("createdAt"), minCreatedAt, maxCreatedAt);
            }
        };
    }
    
    // 組合多條件查詢
    public static Specification<TransactionRecord> filterTranscationRecords(TranscationRecordDTO dto) {
        Specification<TransactionRecord> spec = Specification.where(null);

        // 添加 houseId 查詢條件
        if (dto.getHouseId() != null) {
            spec = spec.and(filterByHouseId(dto.getHouseId()));
        }

        // 添加 userId 查詢條件
        if (dto.getUserId() != null) {
            spec = spec.and(filterByUserId(dto.getUserId()));
        }

        // 添加 cashFlow 查詢條件
        if (dto.getCashFlow() != null) {
            spec = spec.and(filterByCashFlow(dto.getCashFlow()));
        }

        // 添加 deal 查詢條件
        if (dto.getDeal() != null && !dto.getDeal().isEmpty()) {
            spec = spec.and(filterByDeal(dto.getDeal()));
        }

        // 添加 platformIncome 查詢條件
        if (dto.getPlatformIncome() != null) {
            spec = spec.and(filterByPlatformIncome(dto.getPlatformIncome()));
        }

        // 添加 CreatedAt 範圍查詢條件
        if (dto.getMinCreatedAt() != null||dto.getMaxCreatedAt() != null) {
            spec = spec.and(hasCreatedAtBetween(dto.getMinCreatedAt(),dto.getMaxCreatedAt()));
        }
        
        return spec;
    }

    // 輔助方法檢查加入條件
    // private static <T> Specification<TransactionRecord> addIfNotNull(Specification<TransactionRecord> spec, T value,
    //         Function<T, Specification<TransactionRecord>> func) {
    //     return value != null ? spec.and(func.apply(value)) : spec;
    // }

}
