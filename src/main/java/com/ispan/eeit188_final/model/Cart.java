package com.ispan.eeit188_final.model;

import java.sql.Timestamp;

import com.ispan.eeit188_final.model.composite.CartId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

	@EmbeddedId
	private CartId id;
	
//	private UUID userId;
//	private UUID houseId;
	@Column(name = "created_at", columnDefinition = "datetime2")
	private Timestamp createdAt;
	
	
}
