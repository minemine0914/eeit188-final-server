package com.ispan.eeit188_final.service;

import com.ispan.eeit188_final.model.TransactionRecord;
import com.ispan.eeit188_final.repository.TransactionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionRecordService {

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    public ResponseEntity<String> createTransactionRecord(String jsonRequest) {
        if (jsonRequest != null && !jsonRequest.isEmpty()) {
            try {
                JSONObject obj = new JSONObject(jsonRequest);

                UUID houseId = obj.isNull("houseId") ? null : UUID.fromString(obj.getString("houseId"));
                UUID userId = obj.isNull("userId") ? null : UUID.fromString(obj.getString("userId"));
                int cashFlow = obj.isNull("cashFlow") ? 0 : obj.getInt("cashFlow");
                String deal = obj.isNull("deal") ? null : obj.getString("deal");

                if (houseId == null) {
                    return ResponseEntity.badRequest().body("{\"message\": \"House ID can't be null\"}");
                }

                if (userId == null) {
                    return ResponseEntity.badRequest().body("{\"message\": \"User ID can't be null\"}");
                }

                if (deal == null || deal.isEmpty()) {
                    return ResponseEntity.badRequest().body("{\"message\": \"Deal can't be null or empty string\"}");
                }

                TransactionRecord transactionRecord = new TransactionRecord();
                transactionRecord.setHouseId(houseId);
                transactionRecord.setUserId(userId);
                transactionRecord.setCashFlow(cashFlow);
                transactionRecord.setDeal(deal);
                transactionRecord.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                transactionRecordRepository.save(transactionRecord);

                return ResponseEntity.ok("{\"message\": \"Successfully created transaction record\"}");
            } catch (JSONException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid JSON request\"}");
    }

    public ResponseEntity<String> getTransactionRecord(UUID id) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<TransactionRecord> optional = transactionRecordRepository.findById(id);

            if (optional.isPresent()) {
                TransactionRecord transactionRecord = optional.get();

                try {
                    JSONObject obj = new JSONObject()
                            .put("houseId", transactionRecord.getHouseId())
                            .put("userId", transactionRecord.getUserId())
                            .put("cashFlow", transactionRecord.getCashFlow())
                            .put("deal", transactionRecord.getDeal())
                            .put("createdAt", transactionRecord.getCreatedAt());

                    return ResponseEntity.ok(obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error creating JSON: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"Transaction record not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }

    public List<TransactionRecord> getAllTransactionRecords() {
        return transactionRecordRepository.findAll();
    }

    public List<TransactionRecord> getTransactionRecordsByHouseId(UUID houseId) {
        return transactionRecordRepository.findAll().stream()
                .filter(record -> record.getHouseId().equals(houseId))
                .toList();
    }

    public List<TransactionRecord> getTransactionRecordsByUserId(UUID userId) {
        return transactionRecordRepository.findAll().stream()
                .filter(record -> record.getUserId().equals(userId))
                .toList();
    }

    public ResponseEntity<String> updateTransactionRecord(UUID id, String jsonRequest) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<TransactionRecord> optional = transactionRecordRepository.findById(id);

            if (optional.isPresent()) {
                TransactionRecord transactionRecord = optional.get();

                try {
                    JSONObject obj = new JSONObject(jsonRequest);

                    int cashFlow = obj.isNull("cashFlow") ? 0 : obj.getInt("cashFlow");
                    String deal = obj.isNull("deal") ? null : obj.getString("deal");

                    if (deal == null || deal.isEmpty()) {
                        return ResponseEntity.badRequest().body("{\"message\": \"Deal can't be null or empty string\"}");
                    }

                    transactionRecord.setCashFlow(cashFlow);
                    transactionRecord.setDeal(deal);

                    transactionRecordRepository.save(transactionRecord);

                    return ResponseEntity.ok("{\"message\": \"Successfully updated transaction record\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"Transaction record not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }

    public ResponseEntity<String> deleteTransactionRecord(UUID id) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<TransactionRecord> optional = transactionRecordRepository.findById(id);

            if (optional.isPresent()) {
                transactionRecordRepository.deleteById(id);
                return ResponseEntity.ok("{\"message\": \"Transaction record deleted successfully\"}");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"Transaction record not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }
}
