//package com.ispan.eeit188_final.model;
//
//import java.util.Date;
//import java.util.UUID;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Document
//public class HouseMongo {
//	// 某一個User對某一個House的點擊、愛心、分享
//	@Id
//	public UUID id;
//	public UUID userId;
//	public UUID HouseId;
//	public Boolean clicked;
//	public Boolean liked;
//	public Boolean shared;
//	public Date clickDate;
////	public Integer[] score;
//
//	public HouseMongo() {
//		this.id = UUID.randomUUID();
//		this.userId = null;
//		this.HouseId = null;
//		this.clicked = false;
//		this.liked = false;
//		this.shared = false;
////		this.score = new Integer[5];
////		for (int i = 0; i < 5; i++) {
////			score[i] = 0;
////		}
//	}
//
//}
