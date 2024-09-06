package com.ispan.eeit188_final.model.composite;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class UserCollectionId implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID userId;
    private UUID houseId;

    public UserCollectionId() {
    }

    public UserCollectionId(UUID userId, UUID houseId) {
        this.userId = userId;
        this.houseId = houseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserCollectionId that = (UserCollectionId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(houseId, that.houseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, houseId);
    }
}
