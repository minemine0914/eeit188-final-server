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

/* House.java
 @ManyToMany
	@JoinTable(name = "house_postulate", 
				inverseJoinColumns = @JoinColumn(name = "postulate_id", referencedColumnName = "id"), 
				 joinColumns= @JoinColumn(name = "house_id", referencedColumnName = "id"))
	@JsonManagedReference
	private Set<Postulate> postulates;
*/

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "house_postulate")
public class HousePostulate {

	@EmbeddedId
	private HousePostulateId id;

	@Column(name = "created_at", columnDefinition = "datetime2")
	private Timestamp createdAt;

	// @MapsId("postulateId")
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "postulate_id", referencedColumnName = "id")
	// @JsonManagedReference("postulate-housePostulate")
	// private Postulate postulate;

	// @MapsId("houseId")
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "house_id", referencedColumnName = "id")
	// @JsonManagedReference("house-housePostulate")
	// private House house;

	@PrePersist
	public void onCreate() {
		this.createdAt = new Timestamp(System.currentTimeMillis());
	}

}
