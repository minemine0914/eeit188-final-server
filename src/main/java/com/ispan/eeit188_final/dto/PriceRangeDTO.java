package com.ispan.eeit188_final.dto;

import java.sql.Timestamp;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PriceRangeDTO {
    // 用於 Entity 資料
    private UUID id;
    private Integer newPrice;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private UUID houseId;
    // 用於 查詢
    private Integer minPrice;
    private Integer maxPrice;
    // 用於分頁、排序
    private Integer page;
    private Integer limit;
    private Boolean dir;
    private String order;
}
