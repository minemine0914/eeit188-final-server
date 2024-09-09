package com.ispan.eeit188_final.service;

import com.ispan.eeit188_final.dto.DiscussDTO;
import com.ispan.eeit188_final.model.Discuss;
import com.ispan.eeit188_final.repository.DiscussRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscussService {

    @Autowired
    private DiscussRepository discussRepository;

    public Discuss createDiscuss(DiscussDTO discussDTO) {
        Discuss discuss = convertDtoToEntity(discussDTO);
        return discussRepository.save(discuss);
    }

    public Optional<Discuss> getDiscuss(UUID id) {
        return discussRepository.findById(id);
    }

    public Page<Discuss> getDiscussionsByHouseId(UUID houseId, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return discussRepository.findByHouseId(houseId, pageRequest);
    }

    public Page<Discuss> getDiscussionsByUserId(UUID userId, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return discussRepository.findByUserId(userId, pageRequest);
    }

    public Optional<Discuss> retractDiscuss(UUID id) {
        Optional<Discuss> optionalDiscuss = discussRepository.findById(id);
        if (optionalDiscuss.isPresent()) {
            Discuss discuss = optionalDiscuss.get();
            discuss.setShow(false);
            return Optional.of(discussRepository.save(discuss));
        }
        return Optional.empty();
    }

    // Utility method to convert DiscussDTO to Discuss entity
    private Discuss convertDtoToEntity(DiscussDTO discussDTO) {
        Discuss discuss = new Discuss();
        discuss.setDiscuss(discussDTO.getDiscuss());
        discuss.setShow(discussDTO.getShow());
        discuss.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        discuss.setUser(discussDTO.getUserId());
        discuss.setHouse(discussDTO.getHouseId());
        discuss.setDiscussId(discussDTO.getDiscussId());
        return discuss;
    }
}
