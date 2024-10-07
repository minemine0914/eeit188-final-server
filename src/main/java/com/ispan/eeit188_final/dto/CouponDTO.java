package com.ispan.eeit188_final.dto;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(value = Include.NON_NULL)
public class CouponDTO {
    // 基本資訊
    private UUID id;
    // private UUID houseId;
    private UUID userId;
    private String userName;
    private Double discountRate;
    private Integer discount;
    private Integer expire;
    private Timestamp createdAt;
    private String name;
    // search
    private Boolean isExpired;
    // 用於 分頁、排序
    private Integer page;
    private Integer limit;
    private Boolean dir;
    private String order;
}
