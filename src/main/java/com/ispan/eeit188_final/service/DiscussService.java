package com.ispan.eeit188_final.service;

import com.ispan.eeit188_final.dto.DiscussDTO;
import com.ispan.eeit188_final.model.Discuss;
import com.ispan.eeit188_final.model.House;
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

        Discuss newDiscuss = new Discuss();
        newDiscuss.setDiscuss(discussDTO.getDiscuss());
        newDiscuss.setShow(true);
        newDiscuss.setUser(user);
        newDiscuss.setHouse(house);

        // 確認是否為討論板內回覆
        if (discussDTO.getDiscussId() != null && !discussDTO.getDiscussId().toString().isEmpty()) {
            Optional<Discuss> discussOptional = discussRepository.findById(discussDTO.getDiscussId());

            if (discussOptional.isPresent()) {
                Discuss discuss = discussOptional.get();
                newDiscuss.setSubDiscuss(discuss);
            } else {
                return ResponseEntity.badRequest()
                        .body(discussDTO.getDiscussNotFoundException());
            }
        }

        discussRepository.save(newDiscuss);

        return ResponseEntity.ok(newDiscuss);
    }

    public Optional<Discuss> getDiscuss(UUID id) {
        return discussRepository.findById(id);
    }

    public ResponseEntity<String> getDiscussionsByHouseId(UUID houseId, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Discuss> discusses = discussRepository.findByHouseId(houseId, pageRequest);

        JSONArray jsonArray = new JSONArray();

        for (Discuss discuss : discusses) {
            System.out.println(discuss);
            try {
                JSONObject obj = new JSONObject()
                        .put("discuss", discuss.getDiscuss())
                        .put("user", discuss.getUser().getName())
                        .put("avatar", discuss.getUser().getAvatarBase64());

                jsonArray.put(obj);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONObject response = new JSONObject()
                .put("discusses", jsonArray);

        return ResponseEntity.ok(response.toString());
    }

    public ResponseEntity<String> getDiscussionsByUserId(UUID userId, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Discuss> discusses = discussRepository.findByUserId(userId, pageRequest);

        JSONArray jsonArray = new JSONArray();

        for (Discuss discuss : discusses) {
            System.out.println(discuss);
            try {
                JSONObject obj = new JSONObject()
                        .put("id", discuss.getId())
                        .put("discuss", discuss.getDiscuss())
                        .put("house", discuss.getHouse().getName())
                        .put("houseId", discuss.getHouse().getId())
                        .put("externalResourceId", discuss.getHouse().getHouseExternalResourceRecords().get(0).getId());

                jsonArray.put(obj);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONObject response = new JSONObject()
                .put("discusses", jsonArray);

        return ResponseEntity.ok(response.toString());
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
            discuss.setDiscuss(discussDTO.getDiscuss());
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
