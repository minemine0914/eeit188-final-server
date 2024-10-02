package com.ispan.eeit188_final.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import com.ispan.eeit188_final.model.House;

public interface HouseRepository extends JpaRepository<House, UUID>, JpaSpecificationExecutor<House> {

    @NonNull
    @EntityGraph(attributePaths = {"priceRanges", "postulates", "transactionRecords", "tickets", "discusses", "userCollections"})
    Page<House> findAll(@NonNull Specification<House> spec, @NonNull Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths = {"priceRanges", "postulates", "transactionRecords", "tickets", "discusses", "userCollections"})
    Page<House> findAll(@NonNull Pageable pageable); 

    @NonNull
    @EntityGraph(attributePaths = {"priceRanges", "postulates", "transactionRecords", "tickets", "discusses", "userCollections"})
    List<House> findAllById(@NonNull Iterable<UUID> ids);

}
