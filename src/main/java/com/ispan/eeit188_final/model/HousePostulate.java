package com.ispan.eeit188_final.model;

import java.sql.Timestamp;

import com.ispan.eeit188_final.model.composite.HousePostulateId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
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

	@EmbeddedId
	private HousePostulateId id;
	
//	@MapsId("postulateId")
//	private UUID postulateId;
	
//	@MapsId("houseId")
//	private UUID houseId;
	
	@Column(name = "postulate", columnDefinition = "varchar(15)")
	private String postulate;
	
	@Column(name = "createdAt", columnDefinition = "datetime2")
	private Timestamp createdAt;
	
	@PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
	
}
