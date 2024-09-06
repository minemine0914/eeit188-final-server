package com.ispan.eeit188_final.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/platform_staff")
public class PlatformStaffController {
    @Autowired
    private PlatformStaffService psService;

    // 新增
    @PostMapping("/add")
    public ResponseEntity<String> postPlatformStaffService(@RequestBody PlatformStaff platformStaff) {
        if (platformStaff.getBirthday() == null ||
                platformStaff.getPassword() == null || platformStaff.getPassword().isEmpty() ||
                platformStaff.getEmail() == null || platformStaff.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("生日、密碼和信箱不能為空值");
        }

        // 檢查是否已存在相同的 email
        if (psService.existsByEmail(platformStaff.getEmail())) {
            return ResponseEntity.badRequest().body("信箱已存在");
        }

        psService.savePS(platformStaff.getId(), platformStaff.getName(), platformStaff.getRole(),
                platformStaff.getGender(), platformStaff.getBirthday(), platformStaff.getPhone(),
                platformStaff.getMobile_phone(), platformStaff.getAddress(), platformStaff.getEmail(),
                platformStaff.getPassword(), platformStaff.getCreatedAt(), platformStaff.getUpdatedAt(),
                platformStaff.getAvatarbase64(), platformStaff.getBackgroundImageBlob());

        return ResponseEntity.status(HttpStatus.CREATED).body("新增成功");
    }

    // ID 查尋 PlatformStaff
    @GetMapping("/{id}")
    public PlatformStaff getPlatformStaffById(@PathVariable UUID id) {
        return psService.findPSById(id);
    }

    // 更新 PlatformStaff
    @PutMapping("/{id}")
    public PlatformStaff updatePlatformStaff(@RequestBody PlatformStaff platformStaff) {
        return psService.updatePS(platformStaff);
    }

    // 删除 PlatformStaff
    @DeleteMapping("/{id}")
    public void deletePlatformStaffById(@PathVariable UUID id) {
        psService.deleteById(id);
    }

    // 分頁查询 PlatformStaff
    @GetMapping("/all")
    public Page<PlatformStaff> getPlatformStaffs(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return psService.findAlHer(pageNumber, pageSize);
    }
}