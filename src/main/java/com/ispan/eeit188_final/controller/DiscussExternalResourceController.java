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

import com.ispan.eeit188_final.model.PlatformStaff;
import com.ispan.eeit188_final.service.PlatformStaffService;

@RestController
@RequestMapping("/discuss_external_resource")
public class DiscussExternalResourceController {

    @Autowired
    private PlatformStaffService psService;

    // // 新增
    // @PostMapping

    // ID 查尋 PlatformStaff
    @GetMapping("/{id}")
    public PlatformStaff getPlatformStaffById(@PathVariable UUID id) {
        return psService.findPSById(id);
    }

    // 更新 PlatformStaff
    @PutMapping("/")
    public PlatformStaff updatePlatformStaff(@RequestBody PlatformStaff platformStaff) {
        return psService.updatePS(platformStaff);
    }

    // 删除 PlatformStaff
    @DeleteMapping("/{id}")
    public void deletePlatformStaffById(@PathVariable UUID id) {
        psService.deleteById(id);
    }

    // 分頁查询 PlatformStaff
    @GetMapping("/")
    public Page<PlatformStaff> getPlatformStaffs(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return psService.findAlHer(pageNumber, pageSize);
    }
}