package com.ispan.eeit188_final.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@CrossOrigin
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

    // 查詢是否收藏 (返回 JSON KEY: isCollect<Boolean>)
    @GetMapping("/")
    public ResponseEntity<String> existsByUserCollectionId(@RequestParam UUID userId, @RequestParam UUID houseId) {
        return userCollectionService.existsByUserCollectionId(userId, houseId);
    }

    // 創建用戶收藏
    @PostMapping("/")
    public ResponseEntity<String> createUserCollection(@RequestBody String jsonRequeest) {
        return userCollectionService.createUserCollection(jsonRequeest);
    }

    // 取消用戶收藏
    @PostMapping("/delete")
    public ResponseEntity<String> removeUserCollection(@RequestBody String jsonRequeest) {
        return userCollectionService.removeUserCollection(jsonRequeest);
    }

    // 查詢特定房源的總收藏數
    @GetMapping("/from-house/total-count/{houseId}")
    public ResponseEntity<String> countAllCollectionsFromHouse(@PathVariable UUID houseId) {

        return userCollectionService.countTotalCollectionsFromHouse(houseId);
    }

    // 查詢特定使用者的總收藏數
    @GetMapping("/from-user/total-count/{userId}")
    public ResponseEntity<String> countAllCollectionsFromUser(@PathVariable UUID userId) {

        return userCollectionService.countTotalCollectionsFromUser(userId);
    }
}
