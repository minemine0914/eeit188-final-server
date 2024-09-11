package com.ispan.eeit188_final.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

import com.ispan.eeit188_final.dto.HouseDTO;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.Postulate;
import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.HouseRepository;
import com.ispan.eeit188_final.repository.PostulateRepository;
import com.ispan.eeit188_final.repository.UserRepository;
import com.ispan.eeit188_final.repository.specification.HouseSpecification;

@Service
public class HouseService {
    // 預設值
    private static final Integer PAGEABLE_DEFAULT_PAGE = 0;
    private static final Integer PAGEABLE_DEFAULT_LIMIT = 10;

    @Autowired
    private HouseRepository houseRepo;
    @Autowired
    private PostulateRepository postulateRepo;
    @Autowired
    private UserRepository userRepo;

    // 新增
    public House create(HouseDTO houseDTO) {
        if (houseDTO.getId() != null) {
            Optional<User> findUser = userRepo.findById(houseDTO.getUserId());
            if (findUser.isPresent()) {
                House house = House.builder()
                        .user(findUser.get())
                        .name(houseDTO.getName())
                        .category(houseDTO.getCategory())
                        .information(houseDTO.getInformation())
                        .latitudeX(houseDTO.getLatitudeX())
                        .longitudeY(houseDTO.getLongitudeY())
                        .country(houseDTO.getCountry())
                        .city(houseDTO.getCity())
                        .region(houseDTO.getRegion())
                        .address(houseDTO.getAddress())
                        .price(houseDTO.getPrice())
                        .livingDiningRoom(houseDTO.getLivingDiningRoom())
                        .bedroom(houseDTO.getBedroom())
                        .restroom(houseDTO.getRestroom())
                        .bathroom(houseDTO.getBathroom())
                        .adult(houseDTO.getAdult())
                        .child(houseDTO.getChild())
                        .pet(houseDTO.getPet())
                        .smoke(houseDTO.getSmoke())
                        .kitchen(houseDTO.getKitchen())
                        .balcony(houseDTO.getBalcony())
                        .show(houseDTO.getShow())
                        .build();
                return houseRepo.save(house);
            }
        }
        return null;
    }

    // 修改
    @Transactional
    public House modify(UUID id, HouseDTO houseDTO) {
        if (id != null) {
            Optional<House> find = houseRepo.findById(id);
            if (find.isPresent()) {
                House modify = find.get();
                // 房源基本資訊
                Optional.ofNullable(houseDTO.getName()).ifPresent(modify::setName);
                Optional.ofNullable(houseDTO.getCategory()).ifPresent(modify::setCategory);
                Optional.ofNullable(houseDTO.getInformation()).ifPresent(modify::setInformation);
                Optional.ofNullable(houseDTO.getLatitudeX()).ifPresent(modify::setLatitudeX);
                Optional.ofNullable(houseDTO.getLongitudeY()).ifPresent(modify::setLongitudeY);
                Optional.ofNullable(houseDTO.getCountry()).ifPresent(modify::setCountry);
                Optional.ofNullable(houseDTO.getCity()).ifPresent(modify::setCity);
                Optional.ofNullable(houseDTO.getRegion()).ifPresent(modify::setRegion);
                Optional.ofNullable(houseDTO.getAddress()).ifPresent(modify::setAddress);
                Optional.ofNullable(houseDTO.getPrice()).ifPresent(modify::setPrice);
                Optional.ofNullable(houseDTO.getPricePerDay()).ifPresent(modify::setPricePerDay);
                Optional.ofNullable(houseDTO.getPricePerWeek()).ifPresent(modify::setPricePerWeek);
                Optional.ofNullable(houseDTO.getPricePerMonth()).ifPresent(modify::setPricePerMonth);
                // 房源基本設施 幾廳 幾房 幾衛 幾浴
                Optional.ofNullable(houseDTO.getLivingDiningRoom()).ifPresent(modify::setLivingDiningRoom);
                Optional.ofNullable(houseDTO.getBedroom()).ifPresent(modify::setBedroom);
                Optional.ofNullable(houseDTO.getRestroom()).ifPresent(modify::setRestroom);
                Optional.ofNullable(houseDTO.getBathroom()).ifPresent(modify::setBathroom);
                Optional.ofNullable(houseDTO.getAdult()).ifPresent(modify::setAdult);
                Optional.ofNullable(houseDTO.getChild()).ifPresent(modify::setChild);
                // 禁止項目
                Optional.ofNullable(houseDTO.getPet()).ifPresent(modify::setPet);
                Optional.ofNullable(houseDTO.getSmoke()).ifPresent(modify::setSmoke);
                // 常態設施
                Optional.ofNullable(houseDTO.getKitchen()).ifPresent(modify::setKitchen);
                Optional.ofNullable(houseDTO.getBalcony()).ifPresent(modify::setBalcony);
                // 狀態 (擁有者不更動)
                Optional.ofNullable(houseDTO.getShow()).ifPresent(modify::setShow);
                // 附加設施
                Optional.ofNullable(houseDTO.getPostulateIds()).ifPresent(postulateIds -> {
                    // 現有的附加設施
                    Set<Postulate> existingPostulates = new HashSet<>(modify.getPostulates());
                    // 查詢新的附加設施
                    List<Postulate> newPostulates = postulateRepo.findAllById(postulateIds);
                    // 如果新的設施列表大小與提供的 ID 列表大小不一致，則拋出異常
                    if (newPostulates.size() != postulateIds.size()) {
                        throw new IllegalArgumentException("部分設施無效，請確認傳入的設施ID是否正確。");
                    }
                    // 計算新的附加設施集合
                    Set<Postulate> newPostulatesSet = new HashSet<>(newPostulates);
                    // 比較現有附加設施和新的附加設施是否相同
                    if (!existingPostulates.equals(newPostulatesSet)) {
                        // 更新附加設施集合
                        modify.getPostulates().clear();
                        modify.getPostulates().addAll(newPostulates);
                        // 手動更新修改時間
                        modify.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    }
                });
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
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(houseDTO.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
        Integer limit = Optional.ofNullable(houseDTO.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
        Boolean dir = Optional.ofNullable(houseDTO.getDir()).orElse(false);
        String order = Optional.ofNullable(houseDTO.getOrder()).orElse(null);
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return houseRepo.findAll(PageRequest.of(page, limit, sort));
    }

    // 條件查詢
    public Page<House> find(HouseDTO houseDTO) {
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(houseDTO.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
        Integer limit = Optional.ofNullable(houseDTO.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
        Boolean dir = Optional.ofNullable(houseDTO.getDir()).orElse(false);
        String order = Optional.ofNullable(houseDTO.getOrder()).orElse(null);
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        return houseRepo.findAll(HouseSpecification.filterHouses(houseDTO), PageRequest.of(page, limit, sort));
    }
}
