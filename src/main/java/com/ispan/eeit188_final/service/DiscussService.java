package com.ispan.eeit188_final.service;

import com.ispan.eeit188_final.dto.DiscussDTO;
import com.ispan.eeit188_final.model.Discuss;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.HouseExternalResource;
import com.ispan.eeit188_final.model.HouseMongo;
import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.DiscussRepository;
import com.ispan.eeit188_final.repository.HouseRepository;
import com.ispan.eeit188_final.repository.UserRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscussService {

    @Autowired
    private DiscussRepository discussRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseMongoService houseMongoService;

    public ResponseEntity<?> createDiscuss(DiscussDTO discussDTO) {
        if (discussDTO.getUserId() == null || discussDTO.getUserId().toString().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(discussDTO.getUserIdNullException());
        }

        if (discussDTO.getHouseId() == null || discussDTO.getHouseId().toString().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(discussDTO.getHouseIdNullException());
        }

        Optional<User> userOptional = userRepository.findById(discussDTO.getUserId());
        Optional<House> houseOptional = houseRepository.findById(discussDTO.getHouseId());

        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(discussDTO.getUserNotFoundException());
        }

        if (!houseOptional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(discussDTO.getHouseNotFoundException());
        }

        User user = userOptional.get();
        House house = houseOptional.get();

        Optional<Discuss> findDiscuss = discussRepository.findByUserIdAndHouseId(user.getId(), house.getId());

        Discuss saveDiscuss;
        if (findDiscuss.isPresent()) {
            saveDiscuss = findDiscuss.get();
            saveDiscuss.setDiscuss(discussDTO.getDiscuss());
            saveDiscuss.setShow(discussDTO.getShow() != null ? true : false);
        } else {
            saveDiscuss = Discuss.builder()
                    .user(user)
                    .house(house)
                    .discuss(discussDTO.getDiscuss())
                    .show(discussDTO.getShow() != null ? true : false)
                    .build();
        }

        // 如果DTO有夾帶分數儲存分數
        if (discussDTO.getScore() != null) {
            HouseMongo findHouseMongo = houseMongoService.findByUserIdAndHouseId(user.getId(), house.getId());
            if (findHouseMongo != null) {
                findHouseMongo.setScore(discussDTO.getScore());
                houseMongoService.update(findHouseMongo);
            } else {
                houseMongoService.create(HouseMongo.builder()
                        .houseId(house.getId())
                        .userId(user.getId())
                        .score(discussDTO.getScore())
                        .build());
            }
        }

        // 確認是否為討論板內回覆
        if (discussDTO.getDiscussId() != null && !discussDTO.getDiscussId().toString().isEmpty()) {
            Optional<Discuss> discussOptional = discussRepository.findById(discussDTO.getDiscussId());

            if (discussOptional.isPresent()) {
                Discuss discuss = discussOptional.get();
                saveDiscuss.setSubDiscuss(discuss);
            } else {
                return ResponseEntity.badRequest()
                        .body(discussDTO.getDiscussNotFoundException());
            }
        }

        // 儲存並返回新增的Discuss
        return ResponseEntity.ok(discussRepository.save(saveDiscuss));
    }

    public Optional<Discuss> getDiscuss(UUID id) {
        return discussRepository.findById(id);
    }

    public ResponseEntity<String> getDiscussionsByHouseId(UUID houseId, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Discuss> discusses = discussRepository.findByHouseId(houseId, pageRequest);
        System.out.println("Discuss count: " + discusses.getNumberOfElements());
        JSONArray jsonArray = new JSONArray();

        for (Discuss discuss : discusses) {
            HouseMongo findHouseMongo = houseMongoService.findByUserIdAndHouseId(discuss.getUser().getId(), houseId);
            System.out.println("Discuss: " + discuss);
            JSONObject obj = new JSONObject()
                    .put("id", discuss.getId())
                    .put("discuss", discuss.getDiscuss() != null ? discuss.getDiscuss() : "")
                    .put("userId", discuss.getUser().getId())
                    .put("user", discuss.getUser().getName())
                    .put("totalDiscussCount", countDiscussionsByUserId(discuss.getUser().getId()))
                    .put("house", discuss.getHouse().getName())
                    .put("houseId", discuss.getHouse().getId())
                    .put("avatar", discuss.getUser().getAvatarBase64())
                    .put("createdAt", discuss.getCreatedAt())
                    .put("updatedAt", discuss.getUpdatedAt())
                    .put("score", findHouseMongo != null ? findHouseMongo.getScore() : null);

            jsonArray.put(obj);
        }

        JSONObject response = new JSONObject()
                .put("discusses", jsonArray)
                .put("totalElements", discusses.getTotalElements())
                .put("totalPages", discusses.getTotalPages())
                .put("numberOfElements", discusses.getNumberOfElements())
                .put("size", discusses.getSize())
                .put("number", discusses.getNumber())
                .put("empty", discusses.getTotalPages() == 0)
                .put("first", discusses.getNumber() == 0)
                .put("last", discusses.getTotalPages() == discusses.getNumber() + 1);

        return ResponseEntity.ok(response.toString());
    }

    public ResponseEntity<String> getDiscussionsByUserId(UUID userId, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Discuss> discusses = discussRepository.findByUserId(userId, pageRequest);

        JSONArray jsonArray = new JSONArray();

        for (Discuss discuss : discusses) {
            System.out.println(discuss);
            HouseMongo findHouseMongo = houseMongoService.findByUserIdAndHouseId(discuss.getUser().getId(),
                    discuss.getHouse().getId());
            List<HouseExternalResource> externalResourceId = discuss.getHouse()
                    .getHouseExternalResourceRecords();
            JSONObject obj = new JSONObject()
                    .put("id", discuss.getId())
                    .put("discuss", discuss.getDiscuss() != null ? discuss.getDiscuss() : "")
                    .put("house", discuss.getHouse().getName())
                    .put("houseId", discuss.getHouse().getId())
                    .put("user", discuss.getUser().getName())
                    .put("userId", discuss.getUser().getId())
                    .put("score", findHouseMongo != null ? findHouseMongo.getScore() : null)
                    .put("createdAt", discuss.getCreatedAt())
                    .put("updatedAt", discuss.getUpdatedAt())
                    .put("externalResourceId",
                            externalResourceId.size() > 0 ? externalResourceId.get(0).getId() : "");

            jsonArray.put(obj);
        }

        JSONObject response = new JSONObject()
                .put("discusses", jsonArray);

        return ResponseEntity.ok(response.toString());
    }

    public ResponseEntity<?> getDiscussionsByUserIdAndHouseId(UUID userId, UUID houseId) {
        Optional<Discuss> find = discussRepository.findByUserIdAndHouseId(userId, houseId);
        if (find.isPresent()) {
            Discuss discuss = find.get();
            HouseMongo findHouseMongo = houseMongoService.findByUserIdAndHouseId(discuss.getUser().getId(),
                    discuss.getHouse().getId());
            JSONObject obj = new JSONObject()
                    .put("id", discuss.getId())
                    .put("discuss", discuss.getDiscuss())
                    .put("userId", discuss.getUser().getId())
                    .put("user", discuss.getUser().getName())
                    .put("houseId", discuss.getHouse().getId())
                    .put("house", discuss.getHouse().getName())
                    .put("avatar", discuss.getUser().getAvatarBase64())
                    .put("score", findHouseMongo != null ? findHouseMongo.getScore() : null);
            return ResponseEntity.ok(obj.toString());
        }
        return ResponseEntity.notFound().build();
    }

    public long countDiscussionsByUserId(UUID userId) {
        return discussRepository.countDiscussionsByUserId(userId);
    }

    public long countDiscussionsByUserIdAndHouseId(UUID userId, UUID houseId) {
        return discussRepository.countDiscussionsByUserIdAndHouseId(userId, houseId);
    }

    public Optional<Discuss> updateDiscuss(UUID id, DiscussDTO discussDTO) {
        Optional<Discuss> optionalDiscuss = discussRepository.findById(id);
        if (optionalDiscuss.isPresent()) {
            Discuss discuss = optionalDiscuss.get();
            User user = discuss.getUser();
            House house = discuss.getHouse();
            discuss.setDiscuss(discussDTO.getDiscuss());
            // 如果DTO有夾帶分數儲存分數
            if (discussDTO.getScore() != null) {
                HouseMongo findHouseMongo = houseMongoService.findByUserIdAndHouseId(user.getId(), house.getId());
                if (findHouseMongo != null) {
                    findHouseMongo.setScore(discussDTO.getScore());
                    houseMongoService.update(findHouseMongo);
                } else {
                    houseMongoService.create(HouseMongo.builder()
                            .houseId(discuss.getId())
                            .userId(user.getId())
                            .score(discussDTO.getScore())
                            .build());
                }
            }
            return Optional.of(discussRepository.save(discuss));
        }
        return Optional.empty();
    }

    public Optional<Discuss> retractDiscuss(UUID id) {
        Optional<Discuss> optionalDiscuss = discussRepository.findById(id);
        if (optionalDiscuss.isPresent()) {
            Discuss discuss = optionalDiscuss.get();
            discuss.setShow(false);
            return Optional.of(discussRepository.save(discuss));
        }
        return Optional.empty();
    }
}
