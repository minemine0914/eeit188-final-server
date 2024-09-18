package com.ispan.eeit188_final.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Optional;
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
                                .put("id", userCollection.getUserCollectionId().getHouse().getId())
                                .put("name", userCollection.getUserCollectionId().getHouse().getName())
                                .put("category", userCollection.getUserCollectionId().getHouse().getCategory())
                                .put("information", userCollection.getUserCollectionId().getHouse().getInformation())
                                .put("latitudeX", userCollection.getUserCollectionId().getHouse().getLatitudeX())
                                .put("longitudeY", userCollection.getUserCollectionId().getHouse().getLongitudeY())
                                .put("country", userCollection.getUserCollectionId().getHouse().getCountry())
                                .put("city", userCollection.getUserCollectionId().getHouse().getCity())
                                .put("region", userCollection.getUserCollectionId().getHouse().getRegion())
                                .put("address", userCollection.getUserCollectionId().getHouse().getAddress())
                                .put("pricePerDay", userCollection.getUserCollectionId().getHouse().getPricePerDay())
                                .put("pricePerWeek",
                                        userCollection.getUserCollectionId().getHouse().getPricePerWeek())
                                .put("pricePerMonth",
                                        userCollection.getUserCollectionId().getHouse().getPricePerMonth());

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

    public ResponseEntity<String> existsByUserCollectionId(UUID userId, UUID houseId) {
        // Convert string IDs to UUIDs
        if (userId != null && houseId != null) {
            Optional<User> findUser = userRepository.findById(userId);
            Optional<House> findHouse = houseRepository.findById(houseId);
            if (findHouse.isPresent() && findUser.isPresent()) {
                UserCollectionId userCollectionId = UserCollectionId.builder()
                        .user(findUser.get())
                        .house(findHouse.get())
                        .build();
                Boolean exists = userCollectionRepository.existsByUserCollectionId(userCollectionId);
                JSONObject successJsonObj = new JSONObject();
                successJsonObj.put("isCollected", exists);
                return ResponseEntity.ok(successJsonObj.toString());
            }
        }
        JSONObject errorJsonObj = new JSONObject();
        errorJsonObj.put("message", "userId and houseId can not be empty!");
        return ResponseEntity.badRequest().body(errorJsonObj.toString());
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

    public ResponseEntity<String> removeUserCollection(String jsonRequest) {
        // Parse Json
        UUID userId = null;
        UUID houseId = null;
        try {
            JSONObject obj = new JSONObject(jsonRequest);
            userId = obj.isNull("userId") ? null : UUID.fromString(obj.getString("userId"));
            houseId = obj.isNull("houseId") ? null : UUID.fromString(obj.getString("houseId"));
        } catch (JSONException e) {
            JSONObject errorJsonObj = new JSONObject();
            errorJsonObj.put("message", "Request Body must be json format!!!");
            return ResponseEntity.badRequest().body(errorJsonObj.toString());
        }
        if (userId != null && houseId != null) {
            Optional<House> findHouse = houseRepository.findById(houseId);
            Optional<User> findUser = userRepository.findById(userId);
            if (findHouse.isPresent() && findUser.isPresent()) {
                UserCollectionId userCollectionId = UserCollectionId.builder()
                        .user(findUser.get())
                        .house(findHouse.get())
                        .build();
                userCollectionRepository.deleteById(userCollectionId);
                return ResponseEntity.noContent().build(); // Return 204 no content
            }
        }
        JSONObject errorJsonObj = new JSONObject();
        errorJsonObj.put("message", "userId and houseId can not be empty!");
        return ResponseEntity.badRequest().body(errorJsonObj.toString()); // Return 403
    }

}