package com.ispan.eeit188_final.repository;  
  
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ispan.eeit188_final.model.DiscussMongo;
  
@Repository  
public interface DiscussMongoRepository extends MongoRepository<DiscussMongo, UUID> {  
	public List<DiscussMongo> findByDiscussId(UUID discussId);
	public List<DiscussMongo> findByUserId(UUID userId);
	public DiscussMongo findByUserIdAndDiscussId(UUID userId, UUID discussId);
  
}