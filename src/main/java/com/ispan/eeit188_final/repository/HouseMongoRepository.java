package com.ispan.eeit188_final.repository;  
  
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ispan.eeit188_final.model.HouseMongo;
import java.util.List;
  
@Repository  
public interface HouseMongoRepository extends MongoRepository<HouseMongo, UUID> {  
	public List<HouseMongo> findByHouseId(UUID houseId);
	public List<HouseMongo> findByUserId(UUID userId);
	public HouseMongo findByUserIdAndHouseId(UUID userId, UUID houseId);
  
}