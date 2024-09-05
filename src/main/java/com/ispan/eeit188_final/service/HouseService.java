package com.ispan.eeit188_final.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.repository.HouseRepository;

@Service
public class HouseService {
    @Autowired
    private HouseRepository houseRepo;

    public House findById(UUID id) {
        Optional<House> find = houseRepo.findById(id);
        if (find.isPresent()) {
            return find.get();
        }
        return null;
    }
}
