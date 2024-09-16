// package com.ispan.eeit188_final.model;

// import java.lang.reflect.Array;
// import java.util.List;
// import java.util.Properties;
// import java.util.UUID;

// import org.geojson.GeoJsonObject;
// import org.geojson.Geometry;
// import org.geojson.LngLatAlt;
// import org.geotools.geojson.GeoJSON;
// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.geo.GeoJson;
// import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
// import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
// import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.data.mongodb.core.mapping.Field;

// import com.fasterxml.jackson.annotation.JsonProperty;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// @Document(collection = "geo_taiwan_cities")
// public class GeoRegionsMongo {
//     @Id
//     @Builder.Default
//     private UUID id = UUID.randomUUID();
//     @Field("type")
//     @JsonProperty("type")
//     private String type;

//     @Field("geometry")
//     @JsonProperty("geometry")
//     private Geometry geometry;

//     @Field("properties")
//     @JsonProperty("properties")
//     private Object properties;

//     // @Getter
//     // @Setter
//     // @Builder
//     // @AllArgsConstructor
//     // @NoArgsConstructor
//     // public static class Geometry {
//     //     @JsonProperty("type")
//     //     private String type;
    
//     //     @JsonProperty("coordinates")
//     //     private Object coordinates;
//     // }
// }


