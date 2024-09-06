package com.ispan.eeit188_final.service;

import com.ispan.eeit188_final.model.Discuss;
import com.ispan.eeit188_final.repository.DiscussRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscussService {

    @Autowired
    private DiscussRepository discussRepository;

    public ResponseEntity<String> createDiscuss(String jsonRequest) {
        if (jsonRequest != null && !jsonRequest.isEmpty()) {
            try {
                JSONObject obj = new JSONObject(jsonRequest);

                String discussText = obj.isNull("discuss") ? null : obj.getString("discuss");
                boolean show = obj.isNull("show") ? false : obj.getBoolean("show");
                UUID userId = obj.isNull("userId") ? null : UUID.fromString(obj.getString("userId"));
                UUID houseId = obj.isNull("houseId") ? null : UUID.fromString(obj.getString("houseId"));
                UUID discussId = obj.isNull("discussId") ? null : UUID.fromString(obj.getString("discussId"));

                if (discussText == null || discussText.isEmpty()) {
                    return ResponseEntity.badRequest().body("{\"message\": \"Discuss text can't be null or empty string\"}");
                }

                if (userId == null) {
                    return ResponseEntity.badRequest().body("{\"message\": \"User ID can't be null\"}");
                }

                if (houseId == null) {
                    return ResponseEntity.badRequest().body("{\"message\": \"House ID can't be null\"}");
                }

                Discuss discuss = new Discuss();
                discuss.setDiscuss(discussText);
                discuss.setShow(show);
                discuss.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                discuss.setUserId(userId);
                discuss.setHouseId(houseId);
                discuss.setDiscussId(discussId);

                discussRepository.save(discuss);

                return ResponseEntity.ok("{\"message\": \"Successfully created discussion\"}");
            } catch (JSONException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid JSON request\"}");
    }

    public ResponseEntity<String> getDiscuss(UUID id) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<Discuss> optional = discussRepository.findById(id);

            if (optional.isPresent()) {
                Discuss discuss = optional.get();

                try {
                    JSONObject obj = new JSONObject()
                            .put("discuss", discuss.getDiscuss())
                            .put("show", discuss.isShow())
                            .put("createdAt", discuss.getCreatedAt())
                            .put("userId", discuss.getUserId())
                            .put("houseId", discuss.getHouseId())
                            .put("discussId", discuss.getDiscussId());

                    return ResponseEntity.ok(obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error creating JSON: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"Discuss not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }

    public List<Discuss> getDiscussionsByHouseId(UUID houseId) {
        return discussRepository.findAll().stream()
                .filter(discuss -> discuss.getHouseId().equals(houseId))
                .toList();
    }

    public List<Discuss> getDiscussionsByUserId(UUID userId) {
        return discussRepository.findAll().stream()
                .filter(discuss -> discuss.getUserId().equals(userId))
                .toList();
    }

    public ResponseEntity<String> updateDiscuss(UUID id, String jsonRequest) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<Discuss> optional = discussRepository.findById(id);

            if (optional.isPresent()) {
                Discuss discuss = optional.get();

                try {
                    JSONObject obj = new JSONObject(jsonRequest);

                    String discussText = obj.isNull("discuss") ? null : obj.getString("discuss");
                    boolean show = obj.isNull("show") ? false : obj.getBoolean("show");

                    if (discussText == null || discussText.isEmpty()) {
                        return ResponseEntity.badRequest().body("{\"message\": \"Discuss text can't be null or empty string\"}");
                    }

                    discuss.setDiscuss(discussText);
                    discuss.setShow(show);

                    discussRepository.save(discuss);

                    return ResponseEntity.ok("{\"message\": \"Successfully updated discussion\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"Discuss not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }

    public ResponseEntity<String> deleteDiscuss(UUID id) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<Discuss> optional = discussRepository.findById(id);

            if (optional.isPresent()) {
                discussRepository.deleteById(id);
                return ResponseEntity.ok("{\"message\": \"Discuss deleted successfully\"}");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"Discuss not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }
}
