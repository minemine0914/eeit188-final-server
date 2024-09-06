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

import com.ispan.eeit188_final.model.Discuss_External_Resource;
import com.ispan.eeit188_final.model.Discuss_External_ResourceRepository;

@Service
public class Discuss_External_ResourceService {

    @Autowired
    private Discuss_External_ResourceRepository DerRepo;

    public Discuss_External_Resource saveHER(UUID id, UUID DiscussId, String url,
            String type, LocalDateTime createdAT) {

        Discuss_External_Resource der = new Discuss_External_Resource();
        der.setId(id);
        der.setDiscussId(DiscussId);
        der.setUrl(url);
        der.setType(type);
        der.setCreatedAt(createdAT);

        return DerRepo.save(der);
    }

    public Discuss_External_Resource findherById(UUID id) {
        Optional<Discuss_External_Resource> optional = DerRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    public void deleteById(UUID id) {
        DerRepo.deleteById(id);
    }

    public Discuss_External_Resource updatePS(Discuss_External_Resource her) {
        return DerRepo.save(her);
    }

    // public List<Discuss_External_Resource> findAllher() {
    // return DerRepo.findAll();
    // }

    public Page<Discuss_External_Resource> findAlHer(int pageNumber, int pageSize) {
        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Discuss_External_Resource> page = DerRepo.findAll(p);
        return page;
    }
}
