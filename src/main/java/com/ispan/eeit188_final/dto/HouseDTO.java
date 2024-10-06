package com.ispan.eeit188_final.dto;

import java.util.List;
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
@JsonInclude(Include.NON_NULL)
public class HouseDTO {
    // 用於 Entity 資料
    private UUID id;
    private String name;
    private String category;
    private String information;
    private Double latitudeX;
    private Double longitudeY;
    private String country;
    private String city;
    private String region;
    private String address;
    private Integer price;
    private Integer pricePerDay;
    private Integer pricePerWeek;
    private Integer pricePerMonth;
    private Short livingDiningRoom;
    private Short bedroom;
    private Short restroom;
    private Short bathroom;
    private Short adult;
    private Short child;
    private Boolean pet;
    private Boolean smoke;
    private Boolean kitchen;
    private Boolean balcony;
    private Boolean show;
    private Boolean review;
    private UUID userId;
    // 用於 查詢過濾
    private Double minLatitudeX;
    private Double maxLatitudeX;
    private Double minLongitudeY;
    private Double maxLongitudeY;
    private Integer minPrice;
    private Integer maxPrice;
    private UUID postulateId;
    private List<UUID> postulateIds;
    private Boolean matchAllPostulates;
    private String userName;
    // 用於 分頁、排序
    private Integer page;
    private Integer limit;
    private Boolean dir;
    private String order;
    // Exception message
    private String message;
}
