package com.ispan.eeit188_final.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ispan.eeit188_final.model.GeoHousePointMongo;

@Repository
public interface GeoHousePointMongoRepository extends MongoRepository<GeoHousePointMongo, UUID>{

}
