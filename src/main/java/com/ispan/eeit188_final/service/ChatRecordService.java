package com.ispan.eeit188_final.service;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.ChatRecord;
import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.ChatRecordRepository;
import com.ispan.eeit188_final.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ChatRecordService {

    @Autowired
    private ChatRecordRepository chatRecordRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> findById(UUID id, int pageNo, int pageSize) {
        if (id == null || id.toString().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"userId can not be null or empty\"}");
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ChatRecord> chatRecords = chatRecordRepository.findAllById(id, pageable);

        if (chatRecords == null || chatRecords.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Chat record not found\"}");
        }

        try {
            JSONArray chatRecordsArray = new JSONArray();

            for (ChatRecord chatRecord : chatRecords.getContent()) {
                JSONObject obj = new JSONObject()
                        .put("id", chatRecord.getId())
                        .put("chat", chatRecord.getChat())
                        .put("senderId", chatRecord.getSender().getId())
                        .put("sender", chatRecord.getSender().getName())
                        .put("receiverId", chatRecord.getReceiver().getId())
                        .put("receiver", chatRecord.getReceiver().getName())
                        .put("createdAt", chatRecord.getCreatedAt());

                chatRecordsArray.put(obj);
            }

            JSONObject response = new JSONObject()
                    .put("chatRecords", chatRecordsArray);

            return ResponseEntity.ok(response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Error creating JSON response: " + e.getMessage() + "\"}");
        }
    }

    public ResponseEntity<String> createRecord(String jsonRequest) {
        if (jsonRequest == null || jsonRequest.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Invalid JSON request\"}");
        }

        try {
            JSONObject obj = new JSONObject(jsonRequest);
            String chat = obj.isNull("chat") ? null : obj.getString("chat");
            String senderId = obj.isNull("senderId") ? null : obj.getString("senderId");
            String receiverId = obj.isNull("receiverId") ? null : obj.getString("receiverId");

            if (chat == null || chat.length() == 0) {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"Chat can't be null or empty\"}");
            }

            if (senderId == null || senderId.length() == 0) {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"senderId can't be null or empty\"}");
            }

            if (receiverId == null || receiverId.length() == 0) {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"receiverId can't be null or empty\"}");
            }

            User sender = null;
            User receiver = null;

            Optional<User> senderOptional = userRepository.findById(UUID.fromString(senderId));
            Optional<User> receiverOptional = userRepository.findById(UUID.fromString(receiverId));

            if (senderOptional.isPresent()) {
                sender = senderOptional.get();
            }

            if (receiverOptional.isPresent()) {
                receiver = receiverOptional.get();
            }

            if (sender == null || receiver == null) {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"Sender and receiver both should not be null\"}");
            }

            ChatRecord chatRecord = new ChatRecord();
            chatRecord.setChat(chat);
            chatRecord.setShow(true);
            chatRecord.setSender(sender);
            chatRecord.setReceiver(receiver);

            chatRecordRepository.save(chatRecord);

            return ResponseEntity.ok("{\"message\": \"Successfully create chat record\"}");
        } catch (JSONException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Error creating JSON response: " + e.getMessage() + "\"}");
        }
    }

    public ResponseEntity<String> retractMessage(UUID id) {
        if (id == null || id.toString().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"chatId can not be null or empty\"}");
        }

        Optional<ChatRecord> optional = chatRecordRepository.findById(id);

        if (!optional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Chat record not found\"}");
        }

        ChatRecord chatRecord = optional.get();
        chatRecord.setShow(false);

        chatRecordRepository.save(chatRecord);

        return ResponseEntity.ok("{\"message\": \"Successfully retract message\"}");
    }
}
