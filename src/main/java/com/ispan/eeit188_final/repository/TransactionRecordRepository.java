package com.ispan.eeit188_final.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.TransactionRecord;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, UUID> {
}
