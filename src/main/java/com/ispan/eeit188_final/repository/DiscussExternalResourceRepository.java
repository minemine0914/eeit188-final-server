package com.ispan.eeit188_final.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.DiscussExternalResource;

import java.util.UUID;

public interface DiscussExternalResourceRepository extends JpaRepository<DiscussExternalResource, UUID> {

}
