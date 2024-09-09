package com.ispan.eeit188_final.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.eeit188_final.service.UserCollectionService;

import java.util.UUID;

@RestController
@RequestMapping("/user-collection")
public class UserCollectionController {

    @Autowired
    private UserCollectionService userCollectionService;

    // 查詢特定用戶所有收藏
    @GetMapping("/from-user/{userId}")
    public ResponseEntity<String> getUserById(@PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        return userCollectionService.findByUserId(userId, pageNo, pageSize);
    }

    // 創建用戶收藏
    @PostMapping("/")
    public ResponseEntity<String> createUserCollection(@RequestBody String jsonRequeest) {

        return userCollectionService.createUserCollection(jsonRequeest);
    }

    // 查詢特定房源的總收藏數
    @GetMapping("/from-house/total-count/{houseId}")
    public ResponseEntity<String> countAllCollectionsFromHouse(@PathVariable UUID houseId) {

        return userCollectionService.countTotalCollectionsFromHouse(houseId);
    }
}
