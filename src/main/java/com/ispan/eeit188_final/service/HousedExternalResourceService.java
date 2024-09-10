package com.ispan.eeit188_final.service;

import java.time.LocalDateTime;
// import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.HousedExternalResource;
import com.ispan.eeit188_final.repository.HousedExternalResourceRepository;

@Service
public class HousedExternalResourceService {

    @Autowired
    private HousedExternalResourceRepository herRepo;

    public HousedExternalResource saveHER(UUID id, UUID house_id, String url,
            String type, LocalDateTime createdAT) {

        HousedExternalResource her = new HousedExternalResource();
        her.setId(id);
        her.setHouseId(house_id);
        her.setUrl(url);
        her.setType(type);
        her.setCreatedAt(createdAT);

        return herRepo.save(her);
    }

    public HousedExternalResource findherById(UUID id) {
        Optional<HousedExternalResource> optional = herRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    public void deleteById(UUID id) {
        herRepo.deleteById(id);
    }

    public HousedExternalResource updateHER(HousedExternalResource her) {
        return herRepo.save(her);
    }

    // public List<HousedExternalResource> findAllher() {
    // return herRepo.findAll();
    // }

    public Page<HousedExternalResource> findAllHer(int pageNumber, int pageSize) {
        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<HousedExternalResource> page = herRepo.findAll(p);
        return page;
    }
}