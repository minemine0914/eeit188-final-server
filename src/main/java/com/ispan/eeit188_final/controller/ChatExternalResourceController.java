package com.ispan.eeit188_final.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.eeit188_final.model.ChatExternalResource;
import com.ispan.eeit188_final.service.ChatExternalResourceService;

@RestController
@RequestMapping("/ChatExternalResource")
public class ChatExternalResourceController {

    @Autowired
    private ChatExternalResourceService cerService;

    // 新增
    @PostMapping("/add")
    public ChatExternalResource createChatExternalResource(@RequestBody ChatExternalResource chatExternalResource) {
        return cerService.saveCER(
                chatExternalResource.getId(),
                chatExternalResource.getChatRecordId(),
                chatExternalResource.getUrl(),
                chatExternalResource.getType(),
                chatExternalResource.getCreatedAt());
    }

    // 根據 ID 查詢
    @GetMapping("/{id}")
    public ChatExternalResource getChatExternalResourceById(@PathVariable UUID id) {
        return cerService.findherById(id);
    }

    // 刪除
    @DeleteMapping("/{id}")
    public void deleteChatExternalResource(@PathVariable UUID id) {
        cerService.deleteById(id);
    }

    // 更新
    @PutMapping("/{id}")
    public ChatExternalResource updateChatExternalResource(@RequestBody ChatExternalResource cer) {

        return cerService.updateCER(cer);
    }

    // 分頁查詢 ChatExternalResource
    @GetMapping("/all")
    public Page<ChatExternalResource> getAllChatExternalResources(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return cerService.findAllCer(pageNumber, pageSize);
    }
}
