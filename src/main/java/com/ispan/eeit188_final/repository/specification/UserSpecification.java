package com.ispan.eeit188_final.repository.specification;

import java.util.UUID;

import org.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;

import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class UserSpecification {

    public static Specification<User> hasId(UUID id) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> id == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<User> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction(); // 如果 name 為 null 或空字串，返回無條件過濾
            }
            return cb.like(root.get("name"), "%" + name + "%"); // 使用 % 符號進行模糊搜尋
        };
    }

    public static Specification<User> filterUsers(String jsonString) {
        JSONObject obj = new JSONObject(jsonString);

        UUID id = obj.isNull("id") ? null : UUID.fromString(obj.getString("id"));
        String name = obj.isNull("name") ? null : obj.getString("name");

        return Specification.where(hasId(id))
                .and(hasName(name));

    }
}
