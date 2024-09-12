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
	// 某一個User對某一個House的點擊、愛心、分享
	@Id
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

	public HouseMongo() {
		this.id = UUID.randomUUID();
		this.userId = null;
		this.houseId = null;
		this.clicked = false;
		this.liked = false;
		this.shared = false;
		this.score = 0;
	}

}
