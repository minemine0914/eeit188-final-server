package com.ispan.eeit188_final.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.eeit188_final.model.Discuss;

public interface DiscussRepository extends JpaRepository<Discuss, UUID> {

        @Query("""
                        SELECT d
                        FROM Discuss d
                        WHERE d.show = true
                        AND d.house.id = :houseId
                        ORDER BY d.createdAt
                        """)
        Page<Discuss> findByHouseId(UUID houseId, Pageable pageable);

        @Query("""
                        SELECT d
                        FROM Discuss d
                        WHERE d.show = true
                        AND d.user.id = :userId
                        ORDER BY d.createdAt
                        """)
        Page<Discuss> findByUserId(UUID userId, Pageable pageable);
}
