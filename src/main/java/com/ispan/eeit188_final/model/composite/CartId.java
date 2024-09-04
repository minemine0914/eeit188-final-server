package com.ispan.eeit188_final.model.composite;

import java.util.Objects;
import java.util.UUID;

import src.main.java.com.ispan.eeit188_final.model.composite.Embeddable;
import src.main.java.com.ispan.eeit188_final.model.composite.Getter;
import src.main.java.com.ispan.eeit188_final.model.composite.NoArgsConstructor;
import src.main.java.com.ispan.eeit188_final.model.composite.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class CartId {

	private UUID userId;
	private UUID houseId;

	@Override
	public int hashCode() {
		return Objects.hash(userId,houseId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj==null || getClass() != obj.getClass()) {
			return false;
		}
		CartId cartId = (CartId) obj;
		return Objects.equals(userId, cartId.userId)
				&&Objects.equals(houseId, cartId.houseId);
	}
	
}
