package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//@Entity
//@Table(name = "cart")
public class Cart {

	
	private UUID userId;
	private UUID houseId;
	private Timestamp createdAt;
	
	
}
