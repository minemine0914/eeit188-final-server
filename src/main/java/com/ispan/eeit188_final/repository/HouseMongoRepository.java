package com.ispan.eeit188_final.repository;  
  
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ispan.eeit188_final.model.HouseMongo;
  
@Repository  
public interface HouseMongoRepository extends MongoRepository<HouseMongo, UUID>, PagingAndSortingRepository<HouseMongo, UUID> {  
	public List<HouseMongo> findByHouseId(UUID houseId);
	public List<HouseMongo> findByUserId(UUID userId);
	public HouseMongo findByUserIdAndHouseId(UUID userId, UUID houseId);
	long countByHouseIdAndLikedTrue(UUID houseId);
	long countByHouseIdAndClickedTrue(UUID houseId);
	long countByHouseIdAndSharedTrue(UUID houseId);
	List<HouseMongo> findByClickedTrue();
	List<HouseMongo> findBySharedTrue();
  
}