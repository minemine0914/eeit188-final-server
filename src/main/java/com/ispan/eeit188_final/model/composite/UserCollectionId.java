package com.ispan.eeit188_final.model.composite;

import java.io.Serializable;
import java.util.Objects;

import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.User;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCollectionId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "uniqueidentifier")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", columnDefinition = "uniqueidentifier")
    private House house;

    // public UserCollectionId() {
    // }

    // public UserCollectionId(User user, House house) {
    //     this.userId = user;
    //     this.houseId = house;
    // }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserCollectionId that = (UserCollectionId) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(house, that.house);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, house);
    }
}
