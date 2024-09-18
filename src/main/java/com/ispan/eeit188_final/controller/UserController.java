package com.ispan.eeit188_final.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import com.ispan.eeit188_final.service.UserService;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 用ID查尋特定用戶
    @GetMapping("/find/{id}")
    public ResponseEntity<String> getUserById(@PathVariable UUID id) {

        return userService.findById(id);
    }

    // 批量查尋用戶
    @GetMapping("/find-users/")
    public ResponseEntity<String> getUsers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        return userService.getUsers(pageNo, pageSize);
    }

    // 創建新用戶
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(
            @RequestBody String jsonRequest) {

        return userService.createUser(jsonRequest);
    }

    // 登入功能
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String jsonRequest) throws JSONException {

        return userService.login(jsonRequest);
    }

    // 刪除用戶
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {

        return userService.deleteById(id);
    }

    // 更新用戶資訊
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable UUID id,
            @RequestBody String jsonRequest) {

        return userService.update(id, jsonRequest);
    }

    // 密碼驗證（更新密碼前的驗證機制）
    @PostMapping("/check-password/{id}")
    public ResponseEntity<String> checkPassword(
            @PathVariable UUID id,
            @RequestBody String jsonRequest) {

        return userService.checkPassword(id, jsonRequest);
    }

    // 忘記密碼（發送密碼更新連結到指定email）
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String jsonRequest) {

        return userService.forgotPassword(jsonRequest);
    }

    // 設定新密碼
    @PutMapping("/set-new-password/{id}")
    public ResponseEntity<String> setNewPassword(
            @PathVariable UUID id,
            @RequestBody String jsonRequest) {

        return userService.setNewPassword(id, jsonRequest);
    }

    // 上傳大頭貼圖片（base64格式）
    @PutMapping("/upload-avatar/{id}")
    public ResponseEntity<String> uploadAvater(@PathVariable UUID id,
            @RequestBody String jsonRequest) {

        return userService.uploadAvater(id, jsonRequest);
    }

    // 上傳個人主頁背景圖片（byte[]）
    @PostMapping("/upload-background-image/{id}")
    public ResponseEntity<String> uploadBackgroundImageBlob(@PathVariable UUID id,
            @RequestParam("file") MultipartFile file) throws IOException {

        return userService.uploadBackgroundImage(id, file);
    }

    // 下載個人主頁背景圖片（byte[]）
    @GetMapping("/download-background-image/{id}")
    public ResponseEntity<Resource> downloadBackgroundImage(@PathVariable UUID id) {

        return userService.getBackgroundImage(id);
    }

    // 移除大頭貼圖片
    @PutMapping("/remove-avatar/{id}")
    public ResponseEntity<String> removeAvatar(@PathVariable UUID id) {

        return userService.deleteAvatar(id);
    }

    // 移除個人主頁背景圖片
    @PutMapping("/remove-background-image/{id}")
    public ResponseEntity<String> removeBackgroundImage(@PathVariable UUID id) {

        return userService.deleteBackgroundImage(id);
    }
}
