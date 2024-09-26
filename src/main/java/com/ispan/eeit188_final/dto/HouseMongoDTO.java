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
	// 紀錄某User是否對某House按過: 愛心, 點擊, 分享, 評分
	private UUID id;
	private UUID userId;
	private UUID houseId;
	private Boolean clicked;
	private Boolean liked;
	private Boolean shared;
	private Integer score;
	private Date clickDate;
	private Date likeDate;
	private Date shareDate;
	private Date scoreDate;
	
    // 用於分頁、排序
    private Integer page;
    private Integer limit;
    private Boolean dir;
    private String order;

    private Integer randomFactor;
    private Boolean ignoreNull;
//    private Boolean getHouseObject;
//    private String getHouseId;
    
}
