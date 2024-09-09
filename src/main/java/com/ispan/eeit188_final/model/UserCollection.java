package com.ispan.eeit188_final.model;

import java.sql.Timestamp;

import com.ispan.eeit188_final.model.composite.UserCollectionId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user_collection")
public class UserCollection {

    @EmbeddedId
    private UserCollectionId userCollectionId;

    @Column(name = "created_at", columnDefinition = "datetime2")
    private Timestamp createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
