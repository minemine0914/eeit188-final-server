package com.ispan.eeit188_final.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import com.ispan.eeit188_final.dto.HouseDTO;
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
                Optional.ofNullable(house.getName()).ifPresent(modify::setName);
                Optional.ofNullable(house.getCategory()).ifPresent(modify::setCategory);
                Optional.ofNullable(house.getInformation()).ifPresent(modify::setInformation);
                Optional.ofNullable(house.getLatitudeX()).ifPresent(modify::setLatitudeX);
                Optional.ofNullable(house.getLongitudeY()).ifPresent(modify::setLongitudeY);
                Optional.ofNullable(house.getCountry()).ifPresent(modify::setCountry);
                Optional.ofNullable(house.getCity()).ifPresent(modify::setCity);
                Optional.ofNullable(house.getRegion()).ifPresent(modify::setRegion);
                Optional.ofNullable(house.getAddress()).ifPresent(modify::setAddress);
                Optional.ofNullable(house.getPrice()).ifPresent(modify::setPrice);

                // 房源基本設施 幾廳 幾房 幾衛 幾浴
                Optional.ofNullable(house.getLivingDiningRoom()).ifPresent(modify::setLivingDiningRoom);
                Optional.ofNullable(house.getBedroom()).ifPresent(modify::setBedroom);
                Optional.ofNullable(house.getRestroom()).ifPresent(modify::setRestroom);
                Optional.ofNullable(house.getBathroom()).ifPresent(modify::setBathroom);

                // 常態設施
                Optional.ofNullable(house.getKitchen()).ifPresent(modify::setKitchen);
                Optional.ofNullable(house.getBalcony()).ifPresent(modify::setBalcony);

                // 狀態 (擁有者不更動)
                Optional.ofNullable(house.getShow()).ifPresent(modify::setShow);
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
    public Page<House> findAll(HouseDTO houseDTO) {
        // 預設 頁數 限制
        Integer defaultPage = 0;
        Integer defaultLimit = 10;
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(houseDTO.getPage()).orElse(defaultPage);
        Integer limit = Optional.ofNullable(houseDTO.getLimit()).orElse(defaultLimit);
        Boolean dir = Optional.ofNullable(houseDTO.getDir()).orElse(false);
        String order = Optional.ofNullable(houseDTO.getOrder()).orElse(null);
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return houseRepo.findAll(PageRequest.of(page, limit, sort));
    }

    // 條件查詢
    public Page<House> find(HouseDTO houseDTO) {
        // 預設 頁數 限制
        Integer defaultPage = 0;
        Integer defaultLimit = 10;
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(houseDTO.getPage()).orElse(defaultPage);
        Integer limit = Optional.ofNullable(houseDTO.getLimit()).orElse(defaultLimit);
        Boolean dir = Optional.ofNullable(houseDTO.getDir()).orElse(false);
        String order = Optional.ofNullable(houseDTO.getOrder()).orElse(null);
        // 條件查詢
        Specification<House> spec = Specification.where(null);
        spec = spec.and(HouseSpecification.filterHouses(houseDTO));
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return houseRepo.findAll(spec, PageRequest.of(page, limit, sort));
    }

}
