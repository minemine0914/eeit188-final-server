package com.ispan.eeit188_final.model.composite;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class HousePostulateId {

	private UUID postulateId;
	private UUID houseId;

	@Override
	public int hashCode() {
		return Objects.hash(postulateId,houseId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj==null || getClass() != obj.getClass()) {
			return false;
		}
		HousePostulateId housePostulateId = (HousePostulateId) obj;
		return Objects.equals(postulateId, housePostulateId.postulateId)
				&&Objects.equals(houseId, housePostulateId.houseId);
	}

}