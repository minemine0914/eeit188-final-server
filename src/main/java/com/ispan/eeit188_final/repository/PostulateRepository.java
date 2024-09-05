package com.ispan.eeit188_final.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.HousePostulate;
import com.ispan.eeit188_final.model.composite.HousePostulateId;

public interface PostulateRepository extends JpaRepository<HousePostulate, HousePostulateId> {

}
