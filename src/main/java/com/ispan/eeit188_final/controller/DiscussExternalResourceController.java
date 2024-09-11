package com.ispan.eeit188_final.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import com.ispan.eeit188_final.model.DiscussExternalResource;
import com.ispan.eeit188_final.service.DiscussExternalResourceService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/DiscussExternalResource")
@CrossOrigin
public class DiscussExternalResourceController {

    @Autowired
    private DiscussExternalResourceService derService;

    @PostMapping("/add")
    public DiscussExternalResource discussExternalResource(
            @RequestBody DiscussExternalResource discussExternalResource) {

        return derService.saveDER(
                discussExternalResource.getId(),
                discussExternalResource.getDiscussId(),
                discussExternalResource.getUrl(),
                discussExternalResource.getType(),
                discussExternalResource.getCreatedAt());
    }

    // 根據 ID 查詢
    @GetMapping("/{id}")
    public DiscussExternalResource getDiscussExternalResourceById(@PathVariable UUID id) {
        return derService.findderById(id);
    }

    // 刪除
    @DeleteMapping("/{id}")
    public void deleteDiscussExternalResource(@PathVariable UUID id) {
        derService.deleteById(id);
    }

    // 更新
    @PutMapping("/{id}")
    public DiscussExternalResource updateDiscussExternalResource(@RequestBody DiscussExternalResource der) {
        return derService.updateDER(der);
    }

    // 分頁查詢 ChatExternalResource
    @GetMapping("/all")
    public Page<DiscussExternalResource> getAllDiscussExternalResource(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return derService.findAllDer(pageNumber, pageSize);
    }
}
