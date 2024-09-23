package com.ispan.eeit188_final.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Document(collection = "house_click_like_share")
public class HouseMongo {
	// 紀錄某User是否對某House按過: 愛心, 點擊, 分享, 評分
	// 以及紀錄操作時間
	@Id
	@Builder.Default
	private UUID id = UUID.randomUUID();
	private UUID userId;
	private UUID houseId;
	@Builder.Default
	private Boolean clicked = false;
	@Builder.Default
	private Boolean liked = false;
	@Builder.Default
	private Boolean shared = false;
	@Builder.Default
	private Integer score = 0;
	private Date clickDate;
	private Date likeDate;
	private Date shareDate;
	private Date scoreDate;

	// public HouseMongo() {
	// 	this.id = UUID.randomUUID();
	// 	this.userId = null;
	// 	this.houseId = null;
	// 	this.clicked = false;
	// 	this.liked = false;
	// 	this.shared = false;
	// 	this.score = 0;
	// }

}
