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

import com.ispan.eeit188_final.model.Housed_External_Resource;
import com.ispan.eeit188_final.model.Housed_External_ResourceRepository;

@Service
public class Housed_External_ResourceService {

    @Autowired
    private Housed_External_ResourceRepository herRepo;

    public Housed_External_Resource saveHER(UUID id, UUID house_id, String url,
            String type, LocalDateTime createdAT) {

        Housed_External_Resource her = new Housed_External_Resource();
        her.setId(id);
        her.setHouseId(house_id);
        her.setUrl(url);
        her.setType(type);
        her.setCreatedAt(createdAT);

        return herRepo.save(her);
    }

    public Housed_External_Resource findherById(UUID id) {
        Optional<Housed_External_Resource> optional = herRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    public void deleteById(UUID id) {
        herRepo.deleteById(id);
    }

    public Housed_External_Resource updatePS(Housed_External_Resource her) {
        return herRepo.save(her);
    }

    // public List<Housed_External_Resource> findAllher() {
    // return herRepo.findAll();
    // }

    public Page<Housed_External_Resource> findAlHer(int pageNumber, int pageSize) {
        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Housed_External_Resource> page = herRepo.findAll(p);
        return page;
    }
}