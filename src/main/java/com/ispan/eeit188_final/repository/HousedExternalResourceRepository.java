package com.ispan.eeit188_final.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.HousedExternalResource;

import java.util.UUID;

public interface HousedExternalResourceRepository extends JpaRepository<HousedExternalResource, UUID> {

}