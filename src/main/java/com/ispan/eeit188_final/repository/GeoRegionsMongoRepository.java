// package com.ispan.eeit188_final.repository;

// import java.util.List;
// import java.util.UUID;

// import org.springframework.data.geo.Distance;
// import org.springframework.data.geo.Point;
// import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.data.mongodb.repository.Query;
// import org.springframework.stereotype.Repository;

// import com.ispan.eeit188_final.model.GeoRegionsMongo;

// @Repository
// public interface GeoRegionsMongoRepository extends MongoRepository<GeoRegionsMongo, UUID> {
//     // 查詢某一點附近的地區
//     List<GeoRegionsMongo> findByGeometryNear(Point location, Distance distance);

//     // 使用原生查詢查找經緯度在一定範圍內的地區
//     @Query("{ 'geometry': { $geoWithin: { $centerSphere: [ [ ?0, ?1 ], ?2 ] } } }")
//     List<GeoRegionsMongo> findRegionsWithin(double longitude, double latitude, double radius);
// }
