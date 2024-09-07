package com.ispan.eeit188_final.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.model.UserCollection;
import com.ispan.eeit188_final.model.composite.UserCollectionId;
import com.ispan.eeit188_final.repository.HouseRepository;
import com.ispan.eeit188_final.repository.UserCollectionRepository;
import com.ispan.eeit188_final.repository.UserRepository;

import java.util.UUID;

@Service
public class UserCollectionService {

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HouseRepository houseRepository;

    public ResponseEntity<String> findByUserId(UUID userId, int pageNo, int pageSize) {
        if (userId != null && !userId.toString().isEmpty()) {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<UserCollection> userCollections = userCollectionRepository.findAllByUserId(userId, pageable);

            if (userCollections != null && !userCollections.isEmpty()) {

                try {
                    JSONArray userCollectionsArray = new JSONArray();

                    for (UserCollection userCollection : userCollections.getContent()) {
                        JSONObject obj = new JSONObject()
                                .put("id", userCollection.getUserCollectionId().getHouseId().getId());

                        userCollectionsArray.put(obj);
                    }

                    JSONObject response = new JSONObject()
                            .put("userCollections", userCollectionsArray);

                    return ResponseEntity.ok(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error creating JSON response: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"User collection not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }

    public ResponseEntity<String> createUserCollection(String jsonRequest) {
        if (jsonRequest != null && !jsonRequest.isEmpty()) {
            try {
                JSONObject obj = new JSONObject(jsonRequest);

                // Convert string IDs to UUIDs
                UUID userId = obj.isNull("userId") ? null : UUID.fromString(obj.getString("userId"));
                UUID houseId = obj.isNull("houseId") ? null : UUID.fromString(obj.getString("houseId"));

                if (userId == null || userId.toString().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("{\"message\": \"User can't be null or empty\"}");
                }

                if (houseId == null || houseId.toString().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("{\"message\": \"houseId can't be null or empty\"}");
                }

                // Retrieve User and House from the database
                User user = userRepository.findById(userId).orElse(null);
                House house = houseRepository.findById(houseId).orElse(null);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("{\"message\": \"User not found\"}");
                }

                if (house == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("{\"message\": \"House not found\"}");
                }

                // Deal with composite ID
                UserCollectionId userCollectionId = new UserCollectionId(user, house);
                UserCollection newUserCollection = new UserCollection();

                newUserCollection.setUserCollectionId(userCollectionId);
                userCollectionRepository.save(newUserCollection);

                return ResponseEntity.ok("{\"message\": \"Successfully created user collection\"}");
            } catch (JSONException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
            }
        }

        return ResponseEntity.badRequest()
                .body("{\"message\": \"Invalid JSON request\"}");
    }

    public ResponseEntity<String> countTotalCollectionsFromHouse(UUID houseId) {
        if (houseId != null && !houseId.toString().isEmpty()) {

            long total = userCollectionRepository.countByHouseId(houseId);

            try {
                JSONObject response = new JSONObject()
                        .put("totalCollectionsCount", total);

                return ResponseEntity.ok(response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"Error creating JSON response: " + e.getMessage() + "\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }
}