package com.ispan.eeit188_final.dto;

import java.sql.Timestamp;
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
public class TicketDTO {
	private UUID id;
	private String qrCode;
	private UUID userId;
	private UUID houseId;
	private Timestamp startedAt;
	private Timestamp endedAt;
	private Boolean used;
	private Boolean review;
	private Timestamp createdAt;
	private UUID transactionRecordId;
	// 用於 分頁、排序
	private Integer page;
	private Integer limit;
	private Boolean dir;
	private String order;
	private Boolean used;
}
