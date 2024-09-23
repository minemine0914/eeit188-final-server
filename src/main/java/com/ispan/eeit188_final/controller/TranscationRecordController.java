package com.ispan.eeit188_final.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ispan.eeit188_final.dto.TranscationRecordDTO;
import com.ispan.eeit188_final.model.TransactionRecord;
import com.ispan.eeit188_final.service.TransactionRecordService;

@CrossOrigin
@RestController
@RequestMapping("/transcation_record")
public class TranscationRecordController {

    @Autowired
    private TransactionRecordService transactionRecordService;

    /** 新增一筆 */
    @PostMapping("/")
    public ResponseEntity<TransactionRecord> create(@RequestBody TranscationRecordDTO dto) {
        TransactionRecord created = transactionRecordService.create(dto);
        if (created != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(created.getId())
                    .toUri();
            return ResponseEntity.created(location).body(created); // Return 201 Created and redirect to created url
        }
        return ResponseEntity.badRequest().build(); // Return 400 BadRequest
    }

    // 查詢一筆 id
    @GetMapping("/{id}")
    public ResponseEntity<TransactionRecord> getMethodName(@PathVariable UUID id) {
        TransactionRecord find = transactionRecordService.findById(id);
        if (find != null) {
            return ResponseEntity.ok(find); // Return 200
        }
        return ResponseEntity.notFound().build(); // Return 404 NotFound
    }

    /** 查詢所有 */
    @GetMapping("/all")
    public Page<TransactionRecord> all(@ModelAttribute TranscationRecordDTO dto) {
        return transactionRecordService.findAll(dto);
    }

    /** 條件查詢 */
    @PostMapping("/search")
    public Page<TransactionRecord> search(@RequestBody TranscationRecordDTO dto) {
        return transactionRecordService.find(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionRecord> updateTransactionRecord(
            @PathVariable UUID id,
            @RequestBody TranscationRecordDTO dto) {
        TransactionRecord updatedRecord = transactionRecordService.modify(id, dto);
        if (updatedRecord != null) {
            return ResponseEntity.ok(updatedRecord);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
