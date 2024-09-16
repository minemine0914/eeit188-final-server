package com.ispan.eeit188_final.dto;

import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

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
@JsonInclude(Include.NON_NULL)
public class HouseExternalResourceDTO {
    // 用於 Entity 資料
    private UUID id;
    private UUID houseId;
    private String url;
    private String type;
    private Timestamp createAt;
    // 用於 分頁、排序
    private Integer page;
    private Integer limit;
    private Boolean dir;
    private String order;
    // Exception message
    private String message;
}
