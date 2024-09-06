package com.ispan.eeit188_final.repository.specification;

import java.sql.Timestamp;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;

import com.ispan.eeit188_final.model.PriceRange;

public class PriceRangeSpecification {

    // 房源ID
    public static Specification<PriceRange> hasHouse(UUID houseId) {
        return (root, query, cb) -> houseId == null ? cb.conjunction() : cb.equal(root.get("house").get("id"), houseId);
    }

    // 日期區間
    public static Specification<PriceRange> withinDateRange(Timestamp start, Timestamp end) {
        return (root, query, cb) -> cb.and(
                start != null ? cb.greaterThanOrEqualTo(root.get("startedAt"), start) : cb.conjunction(),
                end != null ? cb.lessThanOrEqualTo(root.get("endedAt"), end) : cb.conjunction());
    }

    // 價格區間
    public static Specification<PriceRange> hasPriceBetween(Integer minPrice, Integer maxPrice) {
        return (root, query, cb) -> cb.and(
                minPrice != null ? cb.greaterThanOrEqualTo(root.get("newPrice"), minPrice) : cb.conjunction(),
                maxPrice != null ? cb.lessThanOrEqualTo(root.get("newPrice"), maxPrice) : cb.conjunction());
    }

    // 多條件查詢
    public static Specification<PriceRange> filterPriceRange(String jsonString) {
        // DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        JSONObject obj = new JSONObject(jsonString);
        UUID houseId = obj.isNull("houseId") ? null : UUID.fromString(obj.getString("houseId"));
        Integer minPrice = obj.isNull("minPrice") ? null : obj.getInt("minPrice");
        Integer maxPrice = obj.isNull("maxPrice") ? null : obj.getInt("maxPrice");
        Timestamp startedAt = obj.isNull("startedAt") ? null : Timestamp.valueOf(obj.getString("startedAt"));
        Timestamp endedAt = obj.isNull("endedAt") ? null : Timestamp.valueOf(obj.getString("endedAt"));
        return Specification.where(hasHouse(houseId))
                .and(withinDateRange(startedAt, endedAt))
                .and(hasPriceBetween(minPrice, maxPrice));
    }
}
