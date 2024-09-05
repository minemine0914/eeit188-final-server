package com.ispan.eeit188_final.service;

import com.ispan.eeit188_final.model.Discuss;
import com.ispan.eeit188_final.repository.DiscussRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscussService {

    @Autowired
    private DiscussRepository discussRepository;

    // Create a new discussion
    public Discuss createDiscuss(String discussText, boolean show, UUID userId, UUID houseId, UUID discussId) {
        Discuss discuss = new Discuss();
        discuss.setDiscuss(discussText);
        discuss.setShow(show);
        discuss.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        discuss.setUserId(userId);
        discuss.setHouseId(houseId);
        discuss.setDiscussId(discussId);
        return discussRepository.save(discuss);
    }

    // Retrieve a discussion by ID
    public Optional<Discuss> getDiscuss(UUID id) {
        return discussRepository.findById(id);
    }

    // Retrieve all discussions for a specific house
    public List<Discuss> getDiscussionsByHouseId(UUID houseId) {
        return discussRepository.findAll().stream()
                .filter(discuss -> discuss.getHouseId().equals(houseId))
                .toList();
    }

    // Retrieve all discussions by a specific user
    public List<Discuss> getDiscussionsByUserId(UUID userId) {
        return discussRepository.findAll().stream()
                .filter(discuss -> discuss.getUserId().equals(userId))
                .toList();
    }

    // Update an existing discussion
    public Optional<Discuss> updateDiscuss(UUID id, String discussText, boolean show) {
        Optional<Discuss> optionalDiscuss = discussRepository.findById(id);
        if (optionalDiscuss.isPresent()) {
            Discuss discuss = optionalDiscuss.get();
            discuss.setDiscuss(discussText);
            discuss.setShow(show);
            return Optional.of(discussRepository.save(discuss));
        }
        return Optional.empty();
    }

    // Delete a discussion by ID
    public void deleteDiscuss(UUID id) {
        discussRepository.deleteById(id);
    }
}
