package com.ispan.eeit188_final.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.Discuss;

public interface DiscussRepository extends JpaRepository<Discuss, UUID> {

    Page<Discuss> findByHouseId(UUID houseId, Pageable pageable);

    Page<Discuss> findByUserId(UUID userId, Pageable pageable);
}
