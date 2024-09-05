package com.ispan.eeit188_final.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.ChatRecord;
import java.util.UUID;

public interface ChatRecordRepository extends JpaRepository<ChatRecord, UUID> {

}
