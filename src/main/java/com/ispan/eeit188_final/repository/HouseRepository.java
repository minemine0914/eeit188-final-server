package com.ispan.eeit188_final.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.eeit188_final.model.House;

public interface HouseRepository extends JpaRepository<House, UUID>, JpaSpecificationExecutor<House> {

    @Query("SELECT COUNT(h), " +
            "COUNT(CASE WHEN h.review IS NULL THEN 1 END), " +
            "COUNT(CASE WHEN h.review = false THEN 1 END), " +
            "COUNT(CASE WHEN h.review = true THEN 1 END), " +
            "COUNT(CASE WHEN h.show = true THEN 1 END), " +
            "COUNT(CASE WHEN h.show = false THEN 1 END) " +
            "FROM House h WHERE h.user.id = :userId")
    Object[] getHouseStatisticsByUserId(@Param("userId") UUID userId);

}
