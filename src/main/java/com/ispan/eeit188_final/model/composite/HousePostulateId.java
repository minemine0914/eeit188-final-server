package com.ispan.eeit188_final.model.composite;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class HousePostulateId implements Serializable{

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "postulate_id",columnDefinition = "uniqueidentifier")
	// private Postulate postulateId;
	//
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "house_id",columnDefinition = "uniqueidentifier")
	// private House houseId;
	//
	// public HousePostulateId(Postulate postulateId, House houseId) {
	// this.postulateId = postulateId;
	// this.houseId = houseId;
	// }

	private static final long serialVersionUID = 1L;
	
	@Column(name = "postulate_id")
	private UUID postulateId;
	
	@Column(name = "house_id")
	private UUID houseId;

	public HousePostulateId(UUID postulateId, UUID houseId) {
		this.postulateId = postulateId;
		this.houseId = houseId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(postulateId, houseId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		HousePostulateId housePostulateId = (HousePostulateId) obj;
		return Objects.equals(postulateId, housePostulateId.postulateId)
				&& Objects.equals(houseId, housePostulateId.houseId);
	}

}
