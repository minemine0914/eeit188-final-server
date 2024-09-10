package com.ispan.eeit188_final.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulateDTO {
    private String name;
    private String icon;
    // Search houses by postulateId
    private UUID postulateId;
    // 用於分頁、排序
    private Integer page;
    private Integer limit;
    private Boolean dir;
    private String order;
}
