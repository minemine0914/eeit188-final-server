package com.ispan.eeit188_final.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import com.ispan.eeit188_final.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable UUID id) {

        return userService.findById(id);
    }

    @GetMapping("/")
    public ResponseEntity<String> getUsers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        return userService.getUsers(pageNo, pageSize);
    }

    @PostMapping("/")
    public ResponseEntity<String> createUser(
            @RequestBody String jsonRequest) {

        return userService.createUser(jsonRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String jsonRequest) throws JSONException {

        return userService.login(jsonRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {

        return userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable UUID id,
            @RequestBody String jsonRequest) {

        return userService.update(id, jsonRequest);
    }

    @PutMapping("/{id}/upload-avatar")
    public ResponseEntity<String> uploadAvater(@PathVariable UUID id,
            @RequestBody String jsonRequest) {

        return userService.uploadAvater(id, jsonRequest);
    }

    @PutMapping("/{id}/upload-background-image")
    public ResponseEntity<String> uploadBackgroundImageBlob(@PathVariable UUID id,
            @RequestParam("backgroundImage") MultipartFile backgroundImage) throws IOException {

        return userService.uploadBackgroundImage(id, backgroundImage);
    }

    @PutMapping("/{id}/remove-avatar")
    public ResponseEntity<String> removeAvatar(@PathVariable UUID id) {

        return userService.deleteAvatar(id);
    }

    @PutMapping("/{id}/remove-background-image")
    public ResponseEntity<String> removeBackgroundImage(@PathVariable UUID id) {

        return userService.deleteBackgroundImage(id);
    }
}
