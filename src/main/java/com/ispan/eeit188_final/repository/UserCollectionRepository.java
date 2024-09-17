package com.ispan.eeit188_final.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.eeit188_final.model.UserCollection;
import com.ispan.eeit188_final.model.composite.UserCollectionId;

import java.util.UUID;

public interface UserCollectionRepository extends JpaRepository<UserCollection, UserCollectionId> {

    @Query("SELECT uc FROM UserCollection uc WHERE uc.id.user.id = :userId ORDER BY createdAt DESC")
    Page<UserCollection> findAllByUserId(@Param("userId") UUID userId, Pageable pageable);

    Boolean existsByUserCollectionId(UserCollectionId userCollectionId);

    @Query("SELECT COUNT(uc) FROM UserCollection uc WHERE uc.userCollectionId.house.id = :houseId")
    long countByHouseId(@Param("houseId") UUID houseId);
}
