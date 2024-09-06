package com.ispan.eeit188_final.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.PriceRange;

public interface PriceRangeRepository extends JpaRepository<PriceRange, UUID> {

}
