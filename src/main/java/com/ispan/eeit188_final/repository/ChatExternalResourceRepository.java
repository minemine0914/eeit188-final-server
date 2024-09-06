package com.ispan.eeit188_final.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.ChatExternalResource;

import java.util.UUID;

public interface ChatExternalResourceRepository extends JpaRepository<ChatExternalResource, UUID> {

}
