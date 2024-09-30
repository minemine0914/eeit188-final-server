package com.ispan.eeit188_final.repository.specification;

import java.sql.Timestamp;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;

import com.ispan.eeit188_final.model.Ticket;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class TicketSpecification {

    public static Specification<Ticket> startedBetween(Timestamp minStart, Timestamp maxStart) {
        return (Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (minStart == null && maxStart == null) {
                return criteriaBuilder.conjunction();
            } else if (minStart == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("startedAt"), maxStart);
            } else if (maxStart == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("startedAt"), minStart);
            } else {
                return criteriaBuilder.between(root.get("startedAt"), minStart, maxStart);
            }
        };
    }

    public static Specification<Ticket> endedBetween(Timestamp minEnd, Timestamp maxEnd) {
        return (Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (minEnd == null && maxEnd == null) {
                return criteriaBuilder.conjunction();
            } else if (minEnd == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("endedAt"), maxEnd);
            } else if (maxEnd == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("endedAt"), minEnd);
            } else {
                return criteriaBuilder.between(root.get("endedAt"), minEnd, maxEnd);
            }
        };
    }
    
    public static Specification<Ticket> hasUserId(UUID userId) {
        return (Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> 
        userId == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("userId"), userId);
    }
    
    public static Specification<Ticket> hasHouseId(UUID houseId) {
        return (Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> 
        houseId == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("houseId"), houseId);
    }
    
    public static Specification<Ticket> hasUsed(Boolean used) {
        return (Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> 
        used == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("used"), used);
    }

    public static Specification<Ticket> hasPeople(Integer people) {
        return (Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> 
        people == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("people"), people);
    }
    
    public static Specification<Ticket> filterTickets(String jsonString) {
        JSONObject obj = new JSONObject(jsonString);

        UUID userId = obj.isNull("userId") ? null : UUID.fromString(obj.getString("userId"));
        UUID houseId = obj.isNull("houseId") ? null : UUID.fromString(obj.getString("houseId"));
        Boolean used = obj.isNull("used") ? null : obj.getBoolean("used");
		Integer people = obj.isNull("people") ? null : obj.getInt("people");
        
        Timestamp minStart;
        Timestamp maxStart;
        try {
			minStart = obj.isNull("minStart") ? null : Timestamp.valueOf(obj.getString("minStart"));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("日期格式有誤");
			minStart = null;
		}
        try {
			maxStart = obj.isNull("maxStart") ? null : Timestamp.valueOf(obj.getString("maxStart"));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("日期格式有誤");
			maxStart = null;
		}
        
        
        return Specification.where(hasHouseId(houseId))
                .and(hasUserId(userId))
                .and(hasUsed(used))
                .and(hasPeople(people))
                .and(startedBetween(minStart, maxStart));
                
    }
}
