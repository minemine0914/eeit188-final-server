package com.ispan.eeit188_final.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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
    private Short livingDiningRoom;
    private Short bedroom;
    private Short restroom;
    private Short bathroom;
    private Boolean kitchen;
    private Boolean balcony;
    private Boolean show;
    private UUID userId;
    // 用於 查詢
    private Double minLatitudeX;
    private Double maxLatitudeX;
    private Double minLongitudeY;
    private Double maxLongitudeY;
    private Integer minPrice;
    private Integer maxPrice;
    // 用於分頁、排序
    private Integer page;
    private Integer limit;
    private Boolean dir;
    private String order;
}
