package com.ispan.eeit188_final.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.Postulate;
import java.util.List;

public interface PostulateRepository extends JpaRepository<Postulate, UUID> {

	public List<Postulate> findByName(String name);

}
