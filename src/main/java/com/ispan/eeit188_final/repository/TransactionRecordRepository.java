package com.ispan.eeit188_final.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ispan.eeit188_final.model.TransactionRecord;

public interface TransactionRecordRepository
                extends JpaRepository<TransactionRecord, UUID>, JpaSpecificationExecutor<TransactionRecord> {

        @Query("SELECT tr, h, u FROM TransactionRecord tr " +
                        "JOIN tr.house h " +
                        "JOIN tr.user u ")
        List<Object[]> findAllTransactionRecords();
}
