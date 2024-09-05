package com.ispan.eeit188_final.service;

import com.ispan.eeit188_final.model.TransactionRecord;
import com.ispan.eeit188_final.repository.TransactionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionRecordService {

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    // Create a new transaction record
    public TransactionRecord createTransactionRecord(UUID houseId, UUID userId, int cashFlow, String deal) {
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setHouseId(houseId);
        transactionRecord.setUserId(userId);
        transactionRecord.setCashFlow(cashFlow);
        transactionRecord.setDeal(deal);
        transactionRecord.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return transactionRecordRepository.save(transactionRecord);
    }

    // Retrieve a transaction record by ID
    public Optional<TransactionRecord> getTransactionRecord(UUID id) {
        return transactionRecordRepository.findById(id);
    }

    // Retrieve all transaction records
    public List<TransactionRecord> getAllTransactionRecords() {
        return transactionRecordRepository.findAll();
    }

    // Retrieve all transaction records for a specific house
    public List<TransactionRecord> getTransactionRecordsByHouseId(UUID houseId) {
        return transactionRecordRepository.findAll().stream()
                .filter(record -> record.getHouseId().equals(houseId))
                .toList();
    }

    // Retrieve all transaction records by a specific user
    public List<TransactionRecord> getTransactionRecordsByUserId(UUID userId) {
        return transactionRecordRepository.findAll().stream()
                .filter(record -> record.getUserId().equals(userId))
                .toList();
    }

    // Update an existing transaction record
    public Optional<TransactionRecord> updateTransactionRecord(UUID id, int cashFlow, String deal) {
        Optional<TransactionRecord> optionalTransactionRecord = transactionRecordRepository.findById(id);
        if (optionalTransactionRecord.isPresent()) {
            TransactionRecord transactionRecord = optionalTransactionRecord.get();
            transactionRecord.setCashFlow(cashFlow);
            transactionRecord.setDeal(deal);
            return Optional.of(transactionRecordRepository.save(transactionRecord));
        }
        return Optional.empty();
    }

    // Delete a transaction record by ID
    public void deleteTransactionRecord(UUID id) {
        transactionRecordRepository.deleteById(id);
    }
}
