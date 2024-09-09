package com.ispan.eeit188_final.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.eeit188_final.service.ChatRecordService;

import java.util.UUID;

@RestController
@RequestMapping("/chat-record")
public class ChatRecordController {

    @Autowired
    private ChatRecordService chatRecordService;

    // 查詢特定用戶ID的聊天紀錄（包含發送方和接收方）
    @GetMapping("/{userId}")
    public ResponseEntity<String> findByUserId(@PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        return chatRecordService.findById(userId, pageNo, pageSize);
    }

    // 新增訊息功能
    @PostMapping("/")
    public ResponseEntity<String> createRecord(@RequestBody String jsonRequest) {

        return chatRecordService.createRecord(jsonRequest);
    }

    // 收回訊息功能
    @PutMapping("/retract/{chatId}")
    public ResponseEntity<String> retractMessage(@PathVariable UUID chatId) {

        return chatRecordService.retractMessage(chatId);
    }
}
