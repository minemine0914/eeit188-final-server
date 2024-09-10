package com.ispan.eeit188_final.model;

import java.sql.Timestamp;

import com.ispan.eeit188_final.model.composite.CartId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
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

	@Column(name = "created_at", columnDefinition = "datetime2")
	private Timestamp createdAt;
	
	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id", referencedColumnName = "id")
//	@JsonBackReference
	private User user;

	@MapsId("houseId")
	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "house_id", referencedColumnName = "id")
//	@JsonBackReference
	private House house;
	
	@PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
	
}
