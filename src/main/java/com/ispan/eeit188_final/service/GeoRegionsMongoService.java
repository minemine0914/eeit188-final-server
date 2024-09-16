// package com.ispan.eeit188_final.service;

// import java.util.List;
// import java.util.UUID;

// import org.geotools.feature.FeatureCollection;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.geo.Distance;
// import org.springframework.data.geo.Metrics;
// import org.springframework.data.geo.Point;
// import org.springframework.stereotype.Service;

// import com.ispan.eeit188_final.model.GeoRegionsMongo;
// import com.ispan.eeit188_final.repository.GeoRegionsMongoRepository;

// @Service
// public class GeoRegionsMongoService {

//     @Autowired
//     private GeoRegionsMongoRepository geoRegionsMongoRepository;

//     // 保存或更新 GeoRegion
//     public GeoRegionsMongo saveGeoRegion(GeoRegionsMongo geoRegion) {
//         return geoRegionsMongoRepository.save(geoRegion);
//     }

//     // 根據 ID 查詢 GeoRegion
//     public GeoRegionsMongo getGeoRegionById(UUID id) {
//         return geoRegionsMongoRepository.findById(id).orElse(null);
//     }

//     // 查詢所有 GeoRegion
//     public List<GeoRegionsMongo> getAllGeoRegions() {
//         return geoRegionsMongoRepository.findAll();
//     }

//     // 根據 ID 刪除 GeoRegion
//     public void deleteGeoRegionById(UUID id) {
//         geoRegionsMongoRepository.deleteById(id);
//     }

//     public List<GeoRegionsMongo> findRegionsNear(double longitude, double latitude, double radiusKm) {
//         Point location = new Point(longitude, latitude);
//         Distance distance = new Distance(radiusKm, Metrics.KILOMETERS);
//         return geoRegionsMongoRepository.findByGeometryNear(location, distance);
//     }

//     public List<GeoRegionsMongo> findRegionsWithin(double longitude, double latitude, double radiusKm) {
//         return geoRegionsMongoRepository.findRegionsWithin(longitude, latitude, radiusKm / 6378.1);  // radius in radians
//     }


// }
