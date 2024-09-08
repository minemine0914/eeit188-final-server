package com.ispan.eeit188_final.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.ispan.eeit188_final.model.House;

public interface HouseRepository extends JpaRepository<House, UUID>, JpaSpecificationExecutor<House> {

    // 自定義查詢方法來查詢某個 Postulate 下的所有房源，並支持分頁
    @Query("SELECT h FROM House h JOIN h.postulates p WHERE p.id = :postulateId")
    Page<House> findByPostulateId(@Param("postulateId") UUID postulateId, Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths = {"priceRanges", "postulates"})
    Page<House> findAll(@NonNull Specification<House> spec, @NonNull Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths = {"priceRanges", "postulates"})
    Page<House> findAll(@NonNull Pageable pageable);

}
