package com.ispan.eeit188_final.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.GeoRegionsMongo;
import com.ispan.eeit188_final.repository.GeoRegionsMongoRepository;

@Service
public class GeoRegionsMongoService {

    @Autowired
    private GeoRegionsMongoRepository geoRegionsMongoRepository;

    // 保存或更新 GeoRegion
    public GeoRegionsMongo saveGeoRegion(GeoRegionsMongo geoRegion) {
        return geoRegionsMongoRepository.save(geoRegion);
    }

    // 根據 ID 查詢 GeoRegion
    public GeoRegionsMongo getGeoRegionById(UUID id) {
        return geoRegionsMongoRepository.findById(id).orElse(null);
    }

    // 查詢所有 GeoRegion
    public List<GeoRegionsMongo> getAllGeoRegions() {
        return geoRegionsMongoRepository.findAll();
    }

    // 根據 ID 刪除 GeoRegion
    public void deleteGeoRegionById(UUID id) {
        geoRegionsMongoRepository.deleteById(id);
    }

    // 查詢某經緯度點是否位於某個 Polygon 或 MultiPolygon 範圍內
    public List<GeoRegionsMongo> findRegionsByLocation(double longitude, double latitude) {
        return geoRegionsMongoRepository.findRegionsByLocation(longitude, latitude);
    }

}
