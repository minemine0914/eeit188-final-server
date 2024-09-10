package com.ispan.eeit188_final.dto;

import java.util.Date;
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
public class HouseMongoDTO {
	public UUID id;
	public UUID userId;
	public UUID houseId;
	public Boolean clicked;
	public Boolean liked;
	public Boolean shared;
	public Integer score;
	public Date clickDate;
	public Date likeDate;
	public Date shareDate;
	public Date scoreDate;

    // 用於分頁、排序
    private Integer page;
    private Integer limit;
    private Boolean dir;
    private String order;

}
