package com.ispan.eeit188_final.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.eeit188_final.model.GeoRegionsMongo;

@Repository
public interface GeoRegionsMongoRepository extends MongoRepository<GeoRegionsMongo, UUID> {
    // 查詢某一點是否位於某範圍的 Polygon 或 MultiPolygon 內
    @Query("{ 'geometry': { $geoIntersects: { $geometry: { type: 'Point', coordinates: [?0, ?1] } } } }")
    List<GeoRegionsMongo> findRegionsByLocation(Double longitude, Double latitude);
}
