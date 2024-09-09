package com.ispan.eeit188_final.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.eeit188_final.dto.DiscussDTO;
import com.ispan.eeit188_final.model.Discuss;
import com.ispan.eeit188_final.service.DiscussService;

@RestController
@RequestMapping("/discuss")
public class DiscussController {

    @Autowired
    private DiscussService discussService;

    // 新增討論
    @PostMapping
    public ResponseEntity<Discuss> createDiscuss(@RequestBody DiscussDTO discussDTO) {
        Discuss createdDiscuss = discussService.createDiscuss(discussDTO);
        return ResponseEntity.ok(createdDiscuss);
    }

    // 用ID查詢特定討論
    @GetMapping("/{id}")
    public ResponseEntity<Discuss> getDiscuss(@PathVariable UUID id) {
        Optional<Discuss> discuss = discussService.getDiscuss(id);
        return discuss.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 查詢特定房源所有討論
    @GetMapping("/house/{houseId}")
    public ResponseEntity<Page<Discuss>> getDiscussionsByHouseId(@PathVariable UUID houseId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Discuss> discussions = discussService.getDiscussionsByHouseId(houseId, pageNo, pageSize);
        return ResponseEntity.ok(discussions);
    }

    // 查詢特定使用者所有討論
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Discuss>> getDiscussionsByUserId(@PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Discuss> discussions = discussService.getDiscussionsByUserId(userId, pageNo, pageSize);
        return ResponseEntity.ok(discussions);
    }

    // 收回討論
    @PutMapping("/{id}")
    public ResponseEntity<Discuss> retractDiscuss(@PathVariable UUID id) {
        Optional<Discuss> updatedDiscuss = discussService.retractDiscuss(id);
        return updatedDiscuss.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
