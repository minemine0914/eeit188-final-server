package com.ispan.eeit188_final.repository.specification;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;

import com.ispan.eeit188_final.model.House;

import jakarta.persistence.criteria.Predicate;

public class HouseSpecification {
    // 房源名稱
    public static Specification<House> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction(); // 如果 name 為 null 或空字串，返回無條件過濾
            }
            return cb.like(root.get("name"), "%" + name + "%"); // 使用 % 符號進行模糊搜尋
        };
    }

    // 類型
    public static Specification<House> hasCategory(String category) {
        return (root, query, cb) -> category == null ? cb.conjunction()
                : cb.equal(root.get("category"), category);
    }

    // 國家
    public static Specification<House> hasCountry(String country) {
        return (root, query, cb) -> country == null ? cb.conjunction()
                : cb.equal(root.get("country"), country);
    }

    // 縣市
    public static Specification<House> hasCity(String city) {
        return (root, query, cb) -> city == null ? cb.conjunction()
                : cb.equal(root.get("city"), city);
    }

    // 區域
    public static Specification<House> hasRegion(String region) {
        return (root, query, cb) -> region == null ? cb.conjunction()
                : cb.equal(root.get("region"), region);
    }

    // 價格區間 (最小, 最大)
    public static Specification<House> hasPriceBetween(Integer minPrice, Integer maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) {
                return cb.conjunction();
            } else if (minPrice == null) {
                return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            } else if (maxPrice == null) {
                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else {
                return cb.between(root.get("price"), minPrice, maxPrice);
            }
        };
    }

    // 經緯度區間 (最小經度, 最大經度, 最小緯度, 最大緯度)
    public static Specification<House> isWithinLocation(Double minLatitudeX, Double maxLatitudeX,
            Double minLongitudeY, Double maxLongitudeY) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (minLatitudeX != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("latitudeX"), minLatitudeX));
            }
            if (maxLatitudeX != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("latitudeX"), maxLatitudeX));
            }
            if (minLongitudeY != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("longitudeY"), minLongitudeY));
            }
            if (maxLongitudeY != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("longitudeY"), maxLongitudeY));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // 客廳
    public static Specification<House> hasLivingDiningRoom(Short livingDiningRoom) {
        return (root, query, cb) -> livingDiningRoom == null ? cb.conjunction()
                : cb.equal(root.get("livingDiningRoom"), livingDiningRoom);
    }

    // 房間
    public static Specification<House> hasBedroom(Short bedroom) {
        return (root, query, cb) -> bedroom == null ? cb.conjunction()
                : cb.equal(root.get("bedroom"), bedroom);
    }

    // 衛生間 (馬桶)
    public static Specification<House> hasRestroom(Short restroom) {
        return (root, query, criteriaBuilder) -> restroom == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("restroom"), restroom);
    }

    // 淋浴間
    public static Specification<House> hasBathroom(Short bathroom) {
        return (root, query, criteriaBuilder) -> bathroom == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("bathroom"), bathroom);
    }

    // 廚房
    public static Specification<House> hasKitchen(Boolean kitchen) {
        return (root, query, criteriaBuilder) -> kitchen == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("kitchen"), kitchen);
    }

    // 陽台
    public static Specification<House> hasBalcony(Boolean balcony) {
        return (root, query, cb) -> balcony == null ? cb.conjunction()
                : cb.equal(root.get("balcony"), balcony);
    }

    // 是否上架
    public static Specification<House> isShown(Boolean show) {
        return (root, query, cb) -> show == null ? cb.conjunction()
                : cb.equal(root.get("show"), show);
    }

    // 擁有者ID
    public static Specification<House> hasUserId(UUID userId) {
        return (root, query, cb) -> userId == null ? cb.conjunction()
                : cb.equal(root.get("userId"), userId);
    }

    // 多條件查詢
    public static Specification<House> filterHouses(String jsonString) {
        JSONObject obj = new JSONObject(jsonString);
        // 房源基本資料
        String name = obj.isNull("name") ? null : obj.getString("name");
        String category = obj.isNull("category") ? null : obj.getString("category");
        String country = obj.isNull("country") ? null : obj.getString("country");
        String city = obj.isNull("city") ? null : obj.getString("city");
        String region = obj.isNull("region") ? null : obj.getString("region");
        // 房源基本設施
        Short livingDiningRoom = obj.isNull("city") ? null : Integer.valueOf(obj.getInt("city")).shortValue();
        Short bedroom = obj.isNull("region") ? null : Integer.valueOf(obj.getInt("region")).shortValue();
        Short restroom = obj.isNull("address") ? null : Integer.valueOf(obj.getInt("address")).shortValue();
        Short bathroom = obj.isNull("price") ? null : Integer.valueOf(obj.getInt("price")).shortValue();
        // 常態設施
        Boolean kitchen = obj.isNull("kitchen") ? null : obj.getBoolean("kitchen");
        Boolean balcony = obj.isNull("balcony") ? null : obj.getBoolean("balcony");
        // 刊登顯示
        Boolean show = obj.isNull("show") ? null : obj.getBoolean("show");
        // 擁有者ID
        UUID userId = obj.isNull("userId") ? null : UUID.fromString(obj.getString("userId"));
        // 經緯度區間
        Double minLatitudeX = obj.isNull("minLatitudeX") ? null : obj.getDouble("minLatitudeX");
        Double maxLatitudeX = obj.isNull("maxLatitudeX") ? null : obj.getDouble("maxLatitudeX");
        Double minLongitudeY = obj.isNull("minLongitudeY") ? null : obj.getDouble("minLongitudeY");
        Double maxLongitudeY = obj.isNull("maxLongitudeY") ? null : obj.getDouble("maxLongitudeY");
        // 價格區間
        Integer minPrice = obj.isNull("minPrice") ? null : obj.getInt("minPrice");
        Integer maxPrice = obj.isNull("maxPrice") ? null : obj.getInt("maxPrice");
        return Specification.where(hasName(name))
                .and(hasCategory(category))
                .and(hasCountry(country))
                .and(hasCity(city))
                .and(hasRegion(region))
                .and(hasPriceBetween(minPrice, maxPrice))
                .and(isWithinLocation(minLatitudeX, maxLatitudeX, minLongitudeY, maxLongitudeY))
                .and(hasLivingDiningRoom(livingDiningRoom))
                .and(hasBedroom(bedroom))
                .and(hasRestroom(restroom))
                .and(hasBathroom(bathroom))
                .and(hasKitchen(kitchen))
                .and(hasBalcony(balcony))
                .and(isShown(show))
                .and(hasUserId(userId));
    }
}
