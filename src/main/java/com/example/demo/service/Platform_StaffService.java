package com.ispan.eeit188_final.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.Platform_Staff;
import com.ispan.eeit188_final.model.Platform_StaffRepository;

@Service
public class Platform_StaffService {

    @Autowired
    private Platform_StaffRepository PSRepo;

    public Platform_Staff savePS(UUID id, String name, String role, String gender, Date birthday,
            String phone, String mobile_phone, String address, String email, String password,
            LocalDateTime createdAt, LocalDateTime updatedAt, String headshotImageBase64,
            byte[] backgroundImageBlob) {

        Platform_Staff ps = new Platform_Staff();
        ps.setId(id);
        ps.setName(name);
        ps.setRole(role);
        ps.setGender(gender);
        ps.setBirthday(birthday);
        ps.setPhone(phone);
        ps.setMobile_phone(mobile_phone);
        ps.setAddress(address);
        ps.setEmail(email);
        ps.setPassword(password);
        ps.setCreatedAt(createdAt);
        ps.setUpdatedAt(updatedAt);
        ps.setHeadshotImageBase64(headshotImageBase64);
        ps.setBackgroundImageBlob(backgroundImageBlob);

        return PSRepo.save(ps);

    }

    public Platform_Staff findPSById(UUID id) {
        Optional<Platform_Staff> optional = PSRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    public void deleteById(UUID id) {
        PSRepo.deleteById(id);
    }

    public Platform_Staff updatePS(Platform_Staff PS) {
        return PSRepo.save(PS);
    }

    public List<Platform_Staff> findAllPS() {
        return PSRepo.findAll();
    }

}