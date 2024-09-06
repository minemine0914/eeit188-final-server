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

import com.ispan.eeit188_final.model.ChatExternalResource;
import com.ispan.eeit188_final.repository.ChatExternalResourceRepository;

@Service
public class ChatExternalResourceService {

    @Autowired
    private ChatExternalResourceRepository CerRepo;

    public ChatExternalResource saveHER(UUID id, UUID ChatRecordId, String url,
            String type, LocalDateTime createdAT) {

        ChatExternalResource cer = new ChatExternalResource();
        cer.setId(id);
        cer.setChatRecordId(ChatRecordId);
        cer.setUrl(url);
        cer.setType(type);
        cer.setCreatedAt(createdAT);

        return CerRepo.save(cer);
    }

    public ChatExternalResource findherById(UUID id) {
        Optional<ChatExternalResource> optional = CerRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    public void deleteById(UUID id) {
        CerRepo.deleteById(id);
    }

    public ChatExternalResource updatePS(ChatExternalResource her) {
        return CerRepo.save(her);
    }

    // public List<ChatExternalResource> findAllher() {
    // return CerRepo.findAll();
    // }

    public Page<ChatExternalResource> findAlHer(int pageNumber, int pageSize) {
        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<ChatExternalResource> page = CerRepo.findAll(p);
        return page;
    }
}
