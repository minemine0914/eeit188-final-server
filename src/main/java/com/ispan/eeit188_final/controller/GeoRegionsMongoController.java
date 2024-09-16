// package com.ispan.eeit188_final.controller;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.ispan.eeit188_final.model.GeoRegionsMongo;
// import com.ispan.eeit188_final.service.GeoRegionsMongoService;

// import org.springframework.data.mongodb.core.geo.GeoJsonModule;
// import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;

// import org.geojson.Feature;
// import org.geojson.FeatureCollection;
// import org.geojson.GeoJsonObject;
// import org.geojson.Geometry;
// import org.geojson.GeometryCollection;
// import org.geojson.Polygon;
// import org.geojson.MultiPolygon;

// import java.io.IOException;
// import java.util.List;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.GetMapping;

// @RestController
// @RequestMapping("/geo_regions")
// public class GeoRegionsMongoController {

//     @Autowired
//     private GeoRegionsMongoService geoRegionsMongoService;

//     @PostMapping("/upload")
//     public String uploadGeoJsonFile(@RequestParam("json") MultipartFile collection)
//             throws IOException {
//         FeatureCollection featureCollection = new ObjectMapper().readValue(collection.getInputStream(),
//                 FeatureCollection.class);
//         for (Feature feature : featureCollection.getFeatures()) {
//             GeoJsonObject geoJsonObject = feature.getGeometry();
//             if (geoJsonObject instanceof MultiPolygon) {
//                 // GeoRegionsMongo.Geometry geometry = GeoRegionsMongo.Geometry.builder()
//                 //         .type("MultiPolygon")
//                 //         .coordinates(((MultiPolygon) feature.getGeometry()))
//                 //         .build();
//                 GeoRegionsMongo geoRegionsMongo = GeoRegionsMongo.builder().name(feature.getProperty("name"))
//                         .geometry((MultiPolygon) geoJsonObject).build();
//                 geoRegionsMongoService.saveGeoRegion(geoRegionsMongo);
//             } else if (geoJsonObject instanceof Polygon) {
//                 // GeoRegionsMongo.Geometry geometry = GeoRegionsMongo.Geometry.builder()
//                 //         .type("Polygon")
//                 //         .coordinates(((Polygon) feature.getGeometry()))
//                 //         .build();
//                 GeoRegionsMongo geoRegionsMongo = GeoRegionsMongo.builder().name(feature.getProperty("name"))
//                         .geometry((Polygon) geoJsonObject).build();
//                 geoRegionsMongoService.saveGeoRegion(geoRegionsMongo);
//             }
//         }
//         return null;
//     }

//     @GetMapping("/all")
//     public List<GeoRegionsMongo> getAll() {
//         return geoRegionsMongoService.getAllGeoRegions();
//     }

//     @GetMapping("/find")
//     public List<GeoRegionsMongo> findByLatLong(@RequestParam Double x, @RequestParam Double y) {
//         return geoRegionsMongoService.findRegionsWithin(x, y, 10);
//     }

// }
