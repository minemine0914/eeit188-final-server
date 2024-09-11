package com.ispan.eeit188_final.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

import org.geotools.feature.FeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.util.factory.GeoTools;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.GeoHousePointMongo;
import com.ispan.eeit188_final.repository.GeoHousePointMongoRepository;

@Service
public class GeoHousePointMongoService {

    @Autowired
    private GeoHousePointMongoRepository geoHousePointMongoRepo;

    // public void importGeoJsonToMongo(String filePath) throws IOException {
    //     // 讀取 GeoJSON 文件
    //     FeatureCollection featureCollection = readGeoJsonFile(filePath);

    //     // 將 FeatureCollection 中的每個 Feature 儲存到 MongoDB
    //     try (FeatureIterator<Feature> features = featureCollection.features()) {
    //         while (features.hasNext()) {
    //             Feature feature = features.next();

    //             // 創建 GeoHousePointMongo 實例
    //             GeoHousePointMongo geoHousePoint = new GeoHousePointMongo();
    //             geoHousePoint.setHouseId(UUID.fromString(feature.getID())); // 根據 GeoJSON 的 ID 設置 houseId

    //             // 轉換 Geometry 到 MongoDB GeoJson 格式
    //             com.mongodb.client.model.geojson.Geometry geometry = geometryJSON.readGeometry(feature.getDefaultGeometry().toString());
    //             geoHousePoint.setGeometry((GeoJsonPoint) geometry); // 假設 GeoJsonPoint 直接從 Geometry 轉換過來

    //             // 儲存到 MongoDB
    //             geoHousePointMongoRepo.save(geoHousePoint);
    //         }
    //     }
    // }

    // private FeatureCollection readGeoJsonFile(String filePath) throws IOException {
    //     Ge
    //     FileReader reader = new FileReader(filePath);
    //     return fjson.readFeatureCollection(reader);
    // }

    // JTSFactoryFinder


}
