package com.ispan.eeit188_final.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RequestMapping("/discuss")
public class DiscussController {

    @Autowired
    private DiscussService discussService;

    // 新增討論
    @PostMapping("/")
    public ResponseEntity<?> createDiscuss(@RequestBody DiscussDTO discussDTO) {

        return discussService.createDiscuss(discussDTO);
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
    public ResponseEntity<String> getDiscussionsByHouseId(@PathVariable UUID houseId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        return discussService.getDiscussionsByHouseId(houseId, pageNo, pageSize);
    }

    // 查詢特定使用者所有討論
    @GetMapping("/user/{userId}")
    public ResponseEntity<String> getDiscussionsByUserId(@PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        return discussService.getDiscussionsByUserId(userId, pageNo, pageSize);
    }
    // 查詢特定使用者的特定房源討論
    @GetMapping("/user/{userId}/{houseId}")
    public ResponseEntity<?> getDiscussionsByUserIdAndHouseId(@PathVariable UUID userId, @PathVariable UUID houseId) {

        return discussService.getDiscussionsByUserIdAndHouseId(userId, houseId);
    }

    // 計算特定使用者討論總數
    @GetMapping("/count-from-user/{userId}")
    public long countDiscussionsByUserId(@PathVariable UUID userId) {

        return discussService.countDiscussionsByUserId(userId);
    }

    // 計算特定使用者針對定房源討論總數
    @GetMapping("/count-from-user-and-house/{userId}/{houseId}")
    public long countDiscussionsByUserId(@PathVariable UUID userId, @PathVariable UUID houseId) {

        return discussService.countDiscussionsByUserIdAndHouseId(userId, houseId);
    }

    // 修改討論
    @PutMapping("/update/{id}")
    public ResponseEntity<Discuss> updateDiscuss(@PathVariable UUID id, @RequestBody DiscussDTO discussDTO) {
        Optional<Discuss> updatedDiscuss = discussService.updateDiscuss(id, discussDTO);
        return updatedDiscuss.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 收回討論
    @PutMapping("/retract/{id}")
    public ResponseEntity<Discuss> retractDiscuss(@PathVariable UUID id) {
        Optional<Discuss> updatedDiscuss = discussService.retractDiscuss(id);
        return updatedDiscuss.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
