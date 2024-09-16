package com.ispan.eeit188_final.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.HouseExternalResource;

import java.util.UUID;


public interface HouseExternalResourceRepository extends JpaRepository<HouseExternalResource, UUID> {

    Page<HouseExternalResource> findByHouse(House house, Pageable pageable);

}
