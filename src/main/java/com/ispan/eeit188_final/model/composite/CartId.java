package com.ispan.eeit188_final.model.composite;

import java.io.Serializable;
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
public class CartId implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private UUID userId;
	private UUID houseId;

	public CartId(UUID userId, UUID houseId) {
		this.userId = userId;
		this.houseId = houseId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, houseId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		CartId cartId = (CartId) obj;
		return Objects.equals(userId, cartId.userId)
				&& Objects.equals(houseId, cartId.houseId);
	}

}
