package com.ispan.eeit188_final.service;

import java.time.LocalDateTime;
// import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.DiscussExternalResource;
import com.ispan.eeit188_final.repository.DiscussExternalResourceRepository;

@Service
public class DiscussExternalResourceService {

    @Autowired
    private DiscussExternalResourceRepository DerRepo;

    public DiscussExternalResource saveHER(UUID id, UUID DiscussId, String url,
            String type, LocalDateTime createdAT) {

        DiscussExternalResource der = new DiscussExternalResource();
        der.setId(id);
        der.setDiscussId(DiscussId);
        der.setUrl(url);
        der.setType(type);
        der.setCreatedAt(createdAT);

        return DerRepo.save(der);
    }

    public DiscussExternalResource findherById(UUID id) {
        Optional<DiscussExternalResource> optional = DerRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    public void deleteById(UUID id) {
        DerRepo.deleteById(id);
    }

    public DiscussExternalResource updatePS(DiscussExternalResource her) {
        return DerRepo.save(her);
    }

    // public List<DiscussExternalResource> findAllher() {
    // return DerRepo.findAll();
    // }

    public Page<DiscussExternalResource> findAlHer(int pageNumber, int pageSize) {
        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<DiscussExternalResource> page = DerRepo.findAll(p);
        return page;
    }
}
