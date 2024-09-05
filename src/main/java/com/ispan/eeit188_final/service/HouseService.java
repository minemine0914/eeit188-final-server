package com.ispan.eeit188_final.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.repository.HouseRepository;
import com.ispan.eeit188_final.repository.specification.HouseSpecification;

@Service
public class HouseService {
    @Autowired
    private HouseRepository houseRepo;

    // 新增
    public House create(House house) {
        // TODO: feature: check user_id....
        return houseRepo.save(house);
    }

    // 修改
    public House modify(UUID id, House house) {
        if (id != null) {
            Optional<House> find = houseRepo.findById(id);
            if (find.isPresent()) {
                House modify = find.get();
                // 房源基本資訊
                modify.setName(house.getName());
                modify.setCategory(house.getCategory());
                modify.setInformation(house.getInformation());
                modify.setLatitudeX(house.getLatitudeX());
                modify.setLongitudeY(house.getLongitudeY());
                modify.setCountry(house.getCountry());
                modify.setCity(house.getCity());
                modify.setRegion(house.getRegion());
                modify.setAddress(house.getAddress());
                modify.setPrice(house.getPrice());
                // 房源基本設施 幾廳 幾房 幾衛 幾浴
                modify.setLivingDiningRoom(house.getLivingDiningRoom());
                modify.setBedroom(house.getBedroom());
                modify.setRestroom(house.getRestroom());
                modify.setBathroom(house.getBathroom());
                // 常態設施
                modify.setKitchen(house.getKitchen());
                modify.setBalcony(house.getBalcony());
                // 狀態 (擁有者不更動)
                modify.setShow(house.getShow());
                // 儲存修改
                return houseRepo.save(modify);
            }
        }
        return null;
    }

    // 刪除
    public Boolean delete(UUID id) {
        if (id != null) {
            Optional<House> find = houseRepo.findById(id);
            if (find.isPresent()) {
                houseRepo.deleteById(id);
                return true;
            }
        }
        return false;
    }

    // 查詢
    public House findById(UUID id) {
        if (id != null) {
            Optional<House> find = houseRepo.findById(id);
            if (find.isPresent()) {
                return find.get();
            }
        }
        return null;
    }

    // 查詢所有
    public Page<House> findAll(String jsonString) {
        /*
         * JSON keys: page, limit, dir, order
         */
        JSONObject obj = new JSONObject(jsonString);
        Integer defaultPage = 0;
        Integer defaultLimit = 10;
        Integer page = obj.isNull("page") ? defaultPage : obj.getInt("page");
        Integer limit = obj.isNull("limit") ? defaultLimit : obj.getInt("limit");
        Boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
        String order = obj.isNull("order") ? null : obj.getString("order");
        if (order != null) {
            return houseRepo.findAll(PageRequest.of(page, limit, Sort.by(dir ? Direction.DESC : Direction.ASC, order)));
        }
        return houseRepo.findAll(PageRequest.of(page, limit, Sort.by(dir ? Direction.DESC : Direction.ASC)));
    }

    // 條件查詢
    public Page<House> find(String jsonString) {
        // 預設 頁數 限制
        Integer defaultPage = 0;
        Integer defaultLimit = 10;
        JSONObject obj = new JSONObject(jsonString);
        // 頁數 限制 排序
        Integer page = obj.isNull("page") ? defaultPage : obj.getInt("page");
        Integer limit = obj.isNull("limit") ? defaultLimit : obj.getInt("limit");
        Boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
        String order = obj.isNull("order") ? "id" : obj.getString("order");
        // 條件查詢
        Specification<House> spec = Specification.where(null);
        spec = spec.and(HouseSpecification.filterHouses(jsonString));
        if (order != null) {
            return houseRepo.findAll(spec,
                    PageRequest.of(page, limit, Sort.by(dir ? Direction.DESC : Direction.ASC, order)));
        }
        return houseRepo.findAll(spec, PageRequest.of(page, limit, Sort.by(dir ? Direction.DESC : Direction.ASC)));
    }

}
