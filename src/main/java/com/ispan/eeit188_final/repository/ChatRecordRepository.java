package com.ispan.eeit188_final.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.eeit188_final.model.ChatRecord;
import java.util.UUID;

public interface ChatRecordRepository extends JpaRepository<ChatRecord, UUID> {

    @Query("""
                SELECT cr
                FROM ChatRecord cr
                WHERE cr.show = true
                AND (cr.sender.id = :id OR cr.receiver.id = :id)
                ORDER BY cr.createdAt
            """)
    Page<ChatRecord> findAllById(@Param("id") UUID id, Pageable pageable);
}
