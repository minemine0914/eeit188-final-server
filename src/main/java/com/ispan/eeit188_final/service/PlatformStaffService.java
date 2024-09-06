package com.ispan.eeit188_final.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;
// import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.PlatformStaff;
import com.ispan.eeit188_final.model.PlatformStaffRepository;

@Service
public class PlatformStaffService {

    @Autowired
    private PlatformStaffRepository PSRepo;

    public PlatformStaff savePS(UUID id, String name, String role, String gender, Date birthday,
            String phone, String mobile_phone, String address, String email, String password,
            LocalDateTime createdAt, LocalDateTime updatedAt, String avatarbase64,
            byte[] backgroundImageBlob) {

        PlatformStaff ps = new PlatformStaff();
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
        ps.setAvatarbase64(avatarbase64);
        ps.setBackgroundImageBlob(backgroundImageBlob);

        return PSRepo.save(ps);

    }

    public PlatformStaff findPSById(UUID id) {
        Optional<PlatformStaff> optional = PSRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    public void deleteById(UUID id) {
        PSRepo.deleteById(id);
    }

    public PlatformStaff updatePS(PlatformStaff PS) {
        return PSRepo.save(PS);
    }

    // public List<PlatformStaff> findAllPS() {
    // return PSRepo.findAll();
    // }

    public Page<PlatformStaff> findAlHer(int pageNumber, int pageSize) {
        Pageable p = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<PlatformStaff> page = PSRepo.findAll(p);
        return page;
    }
}