package com.ispan.eeit188_final.controller;

import com.ispan.eeit188_final.model.Discuss;
import com.ispan.eeit188_final.service.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/discuss")
public class DiscussController {

    @Autowired
    private DiscussService discussService;

    @PostMapping
    public ResponseEntity<String> createDiscuss(@RequestBody String jsonRequest) {
        return discussService.createDiscuss(jsonRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getDiscuss(@PathVariable UUID id) {
        return discussService.getDiscuss(id);
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<Discuss>> getDiscussionsByHouseId(@PathVariable UUID houseId) {
        return ResponseEntity.ok(discussService.getDiscussionsByHouseId(houseId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Discuss>> getDiscussionsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(discussService.getDiscussionsByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDiscuss(@PathVariable UUID id, @RequestBody String jsonRequest) {
        return discussService.updateDiscuss(id, jsonRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiscuss(@PathVariable UUID id) {
        return discussService.deleteDiscuss(id);
    }
}
