package com.ispan.eeit188_final.dto;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranscationRecordDTO {
    // 基本資訊
    private UUID id;
    private UUID houseId;
    private UUID userId;
    private Integer cashFlow;
    private String deal;
    private Integer platformIncome;
    private Timestamp createdAt;
    
    // 範圍查詢的上下界
    private Timestamp minCreatedAt;
    private Timestamp maxCreatedAt;
    // 用於 分頁、排序
    private Integer page;
    private Integer limit;
    private Boolean dir;
    private String order;
}
