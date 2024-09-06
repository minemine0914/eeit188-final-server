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

import com.ispan.eeit188_final.model.Chat_External_Resource;
import com.ispan.eeit188_final.model.Chat_External_ResourceRepository;

@Service
public class Chat_External_ResourceService {

    @Autowired
    private Chat_External_ResourceRepository CerRepo;

    public Chat_External_Resource saveHER(UUID id, UUID ChatRecordId, String url,
            String type, LocalDateTime createdAT) {

        Chat_External_Resource cer = new Chat_External_Resource();
        cer.setId(id);
        cer.setChatRecordId(ChatRecordId);
        cer.setUrl(url);
        cer.setType(type);
        cer.setCreatedAt(createdAT);

        return CerRepo.save(cer);
    }

    public Chat_External_Resource findherById(UUID id) {
        Optional<Chat_External_Resource> optional = CerRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    public void deleteById(UUID id) {
        CerRepo.deleteById(id);
    }

    public Chat_External_Resource updatePS(Chat_External_Resource her) {
        return CerRepo.save(her);
    }

    // public List<Chat_External_Resource> findAllher() {
    // return CerRepo.findAll();
    // }

    public Page<Chat_External_Resource> findAlHer(int pageNumber, int pageSize) {
        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Chat_External_Resource> page = CerRepo.findAll(p);
        return page;
    }
}
