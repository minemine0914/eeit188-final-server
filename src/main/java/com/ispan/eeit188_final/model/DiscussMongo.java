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
@Document(collection = "discuss_click_like_share")
public class DiscussMongo {
	// 紀錄某User是否對某Discuss按過: 愛心, 點擊, 分享, 評分
	// 以及紀錄操作時間
	@Id
	private UUID id;
	private UUID userId;
	private UUID discussId;
	private Boolean clicked;
	private Boolean liked;
	private Boolean shared;
	private Integer score;
	private Date clickDate;
	private Date likeDate;
	private Date shareDate;
	private Date scoreDate;

	public DiscussMongo() {
		this.id = UUID.randomUUID();
		this.userId = null;
		this.discussId = null;
		this.clicked = false;
		this.liked = false;
		this.shared = false;
		this.score = 0;
	}

}
