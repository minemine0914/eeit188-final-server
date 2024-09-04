package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "house_postulate")
public class HousePostulate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",columnDefinition = "int")
	private Integer id;
	
	@Column(name = "postulate", columnDefinition = "varchar(15)")
	private String postulate;
	
	@Column(name = "house_id", columnDefinition = "uniqueidentifier")
	private UUID houseId;
	
	@Column(name = "createdAt", columnDefinition = "datetime2")
	private Timestamp createdAt;
	
	
}
