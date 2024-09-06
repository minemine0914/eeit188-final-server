package com.ispan.eeit188_final.repository.specification;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import com.ispan.eeit188_final.dto.HouseDTO;
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
    public static Specification<House> filterHouses(HouseDTO houseDTO) {
        // 房源基本資料
        String name = Optional.ofNullable(houseDTO.getName()).orElse(null);
        String category = Optional.ofNullable(houseDTO.getCategory()).orElse(null);
        String country = Optional.ofNullable(houseDTO.getCountry()).orElse(null);
        String city = Optional.ofNullable(houseDTO.getCity()).orElse(null);
        String region = Optional.ofNullable(houseDTO.getRegion()).orElse(null);
        // 房源基本設施
        Short livingDiningRoom = Optional.ofNullable(houseDTO.getLivingDiningRoom()).orElse(null);
        Short bedroom = Optional.ofNullable(houseDTO.getBedroom()).orElse(null);
        Short restroom = Optional.ofNullable(houseDTO.getRestroom()).orElse(null);
        Short bathroom = Optional.ofNullable(houseDTO.getBathroom()).orElse(null);
        // 常態設施
        Boolean kitchen = Optional.ofNullable(houseDTO.getKitchen()).orElse(null);
        Boolean balcony = Optional.ofNullable(houseDTO.getBalcony()).orElse(null);
        // 刊登顯示
        Boolean show = Optional.ofNullable(houseDTO.getShow()).orElse(null);
        // 擁有者ID
        UUID userId = Optional.ofNullable(houseDTO.getUserId()).orElse(null);
        // 經緯度區間
        Double minLatitudeX = Optional.ofNullable(houseDTO.getMinLatitudeX()).orElse(null);
        Double maxLatitudeX = Optional.ofNullable(houseDTO.getMaxLatitudeX()).orElse(null);
        Double minLongitudeY = Optional.ofNullable(houseDTO.getMinLongitudeY()).orElse(null);
        Double maxLongitudeY = Optional.ofNullable(houseDTO.getMaxLongitudeY()).orElse(null);
        // 價格區間
        Integer minPrice = Optional.ofNullable(houseDTO.getMinPrice()).orElse(null);
        Integer maxPrice = Optional.ofNullable(houseDTO.getMaxPrice()).orElse(null);
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
