package com.ispan.eeit188_final.dto;

import java.sql.Timestamp;
import java.util.UUID;

import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.User;

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

	private Timestamp createdAt;


}
