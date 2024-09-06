package com.ispan.eeit188_final.controller;

import com.ispan.eeit188_final.model.TransactionRecord;
import com.ispan.eeit188_final.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactionRecords")
public class TransactionRecordController {

    @Autowired
    private TransactionRecordService transactionRecordService;

    @PostMapping
    public ResponseEntity<String> createTransactionRecord(@RequestBody String jsonRequest) {
        return transactionRecordService.createTransactionRecord(jsonRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getTransactionRecord(@PathVariable UUID id) {
        return transactionRecordService.getTransactionRecord(id);
    }

    @GetMapping
    public ResponseEntity<List<TransactionRecord>> getAllTransactionRecords() {
        return ResponseEntity.ok(transactionRecordService.getAllTransactionRecords());
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<TransactionRecord>> getTransactionRecordsByHouseId(@PathVariable UUID houseId) {
        return ResponseEntity.ok(transactionRecordService.getTransactionRecordsByHouseId(houseId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionRecord>> getTransactionRecordsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(transactionRecordService.getTransactionRecordsByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTransactionRecord(@PathVariable UUID id, @RequestBody String jsonRequest) {
        return transactionRecordService.updateTransactionRecord(id, jsonRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransactionRecord(@PathVariable UUID id) {
        return transactionRecordService.deleteTransactionRecord(id);
    }
}
