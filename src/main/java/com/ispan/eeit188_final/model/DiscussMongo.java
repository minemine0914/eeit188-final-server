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
	// 某一個User對某一個Discuss的點擊、愛心、分享
	@Id
	public UUID id;
	public UUID userId;
	public UUID discussId;
	public Boolean clicked;
	public Boolean liked;
	public Boolean shared;
	public Integer score;
	public Date clickDate;
	public Date likeDate;
	public Date shareDate;
	public Date scoreDate;

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
