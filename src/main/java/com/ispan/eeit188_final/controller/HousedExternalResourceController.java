package com.ispan.eeit188_final.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.eeit188_final.model.HousedExternalResource;
import com.ispan.eeit188_final.service.HousedExternalResourceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/HousedExternalResource")
public class HousedExternalResourceController {

    @Autowired
    HousedExternalResourceService HerService;

    @PostMapping("/")
    public HousedExternalResource housedExternalResource(@RequestBody HousedExternalResource housedExternalResource) {
        return HerService.saveHER(
                housedExternalResource.getId(),
                housedExternalResource.getHouseId(),
                housedExternalResource.getType(),
                housedExternalResource.getUrl(),
                housedExternalResource.getCreatedAt());
    }

    // 根據 ID 查詢
    @GetMapping("/{id}")
    public HousedExternalResource getHousedExternalResourceById(@PathVariable UUID id) {
        return HerService.findherById(id);
    }

    @DeleteMapping("/{id}")
    public void deletehousedExternalResource(@PathVariable UUID id) {
        HerService.findherById(id);
    }

    @PutMapping("/{id}")
    public HousedExternalResource updateHousedExternalResource(@RequestBody HousedExternalResource her) {

        return HerService.updateHER(her);
    }

    // 分頁查詢 ChatExternalResource
    @GetMapping("/all")
    public Page<HousedExternalResource> getAllChatExternalResources(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return HerService.findAllHer(pageNumber, pageSize);
    }

}
