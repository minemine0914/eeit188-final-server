package com.ispan.eeit188_final.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.PriceRange;
import com.ispan.eeit188_final.repository.HouseRepository;
import com.ispan.eeit188_final.repository.PriceRangeRepository;

@Service
public class PriceRangeService {
    @Autowired
    private PriceRangeRepository priceRangeRepo;

    @Autowired
    private HouseRepository houseRepo;

    public PriceRange create(PriceRange priceRange) {
        // if ( houseRepo.existsById(priceRange.getHouse().getId()) ) {

        // }
        return priceRangeRepo.save(priceRange);
    }

    public PriceRange modify(UUID id, PriceRange priceRange) {
        if ( id != null ) {
            Optional<PriceRange> find = priceRangeRepo.findById(id);
        }
        return null;
    }

    public Boolean delete(UUID id) {

        return false;
    }

    public PriceRange findById(UUID id) {

        return null;
    }

    public Page<PriceRange> findAll (String jsonString) {

        return null;
    }

    public Page<PriceRange> find (String jsonString) {

        return null;
    }

}
