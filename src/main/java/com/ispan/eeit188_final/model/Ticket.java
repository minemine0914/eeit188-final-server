package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id",columnDefinition = "uniqueidentifier")
	private UUID id;
	
	@Column(name = "qrCode", columnDefinition = "varchar(max)")
	private String qrCode;
	
	@Column(name = "userId", columnDefinition = "uniqueidentifier")
	private UUID userId;
	
	@Column(name = "houseId", columnDefinition = "uniqueidentifier")
	private UUID houseId;
	
	@Column(name = "startedAt", columnDefinition = "datetime2")
	private Timestamp startedAt;
	
	@Column(name = "endedAt", columnDefinition = "datetime2")
	private Timestamp endedAt;
	
	@Column(name = "createdAt", columnDefinition = "datetime2")
	private Timestamp createdAt;
	
	@PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
	
}
