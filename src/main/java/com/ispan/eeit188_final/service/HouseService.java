package com.ispan.eeit188_final.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.ispan.eeit188_final.dto.HouseDTO;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.HouseMongo;
import com.ispan.eeit188_final.model.Postulate;
import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.HouseRepository;
import com.ispan.eeit188_final.repository.PostulateRepository;
import com.ispan.eeit188_final.repository.UserRepository;
import com.ispan.eeit188_final.repository.specification.HouseSpecification;

@Service
public class HouseService {
    // 使用 logger
    private static final Logger logger = LoggerFactory.getLogger(HouseService.class);

    // 預設值
    private static final Integer PAGEABLE_DEFAULT_PAGE = 0;
    private static final Integer PAGEABLE_DEFAULT_LIMIT = 10;

    @Autowired
    private HouseRepository houseRepo;
    @Autowired
    private PostulateRepository postulateRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    // 新增
    public House create(HouseDTO houseDTO) {
        Optional<User> findUser = userRepo.findById(houseDTO.getUserId());
        if (findUser.isPresent()) {
            House house = House.builder()
                    .user(findUser.get())
                    .name(Optional.ofNullable(houseDTO.getName()).orElse(null))
                    .category(Optional.ofNullable(houseDTO.getCategory()).orElse("未分類"))
                    .information(Optional.ofNullable(houseDTO.getInformation()).orElse(null))
                    .latitudeX(Optional.ofNullable(houseDTO.getLatitudeX()).orElse(null))
                    .longitudeY(Optional.ofNullable(houseDTO.getLongitudeY()).orElse(null))
                    .country(Optional.ofNullable(houseDTO.getCountry()).orElse(null))
                    .city(Optional.ofNullable(houseDTO.getCity()).orElse(null))
                    .region(Optional.ofNullable(houseDTO.getRegion()).orElse(null))
                    .address(Optional.ofNullable(houseDTO.getAddress()).orElse(null))
                    .price(Optional.ofNullable(houseDTO.getPrice()).orElse(null))
                    .pricePerDay(Optional.ofNullable(houseDTO.getPricePerDay()).orElse(null))
                    .pricePerWeek(Optional.ofNullable(houseDTO.getPricePerWeek()).orElse(null))
                    .pricePerMonth(Optional.ofNullable(houseDTO.getPricePerMonth()).orElse(null))
                    .livingDiningRoom(Optional.ofNullable(houseDTO.getLivingDiningRoom()).orElse((short) 0))
                    .bedroom(Optional.ofNullable(houseDTO.getBedroom()).orElse((short) 0))
                    .restroom(Optional.ofNullable(houseDTO.getRestroom()).orElse((short) 0))
                    .bathroom(Optional.ofNullable(houseDTO.getBathroom()).orElse((short) 0))
                    .adult(Optional.ofNullable(houseDTO.getAdult()).orElse((short) 0))
                    .child(Optional.ofNullable(houseDTO.getChild()).orElse((short) 0))
                    .pet(Optional.ofNullable(houseDTO.getPet()).orElse(false))
                    .smoke(Optional.ofNullable(houseDTO.getSmoke()).orElse(false))
                    .kitchen(Optional.ofNullable(houseDTO.getKitchen()).orElse(false))
                    .balcony(Optional.ofNullable(houseDTO.getBalcony()).orElse(false))
                    .show(Optional.ofNullable(houseDTO.getShow()).orElse(false))
                    .review(Optional.ofNullable(houseDTO.getReview()).orElse(null))
                    .postulates(new HashSet<>())
                    .build();
            // 處理附加設施
            Optional.ofNullable(houseDTO.getPostulateIds()).ifPresent(postulateIds -> {
                List<Postulate> postulates = postulateRepo.findAllById(postulateIds);
                // 如果附加設施的數量和提供的 ID 數量不一致，拋出異常
                if (postulates.size() != postulateIds.size()) {
                    throw new IllegalArgumentException("部分設施無效，請確認傳入的設施ID是否正確。");
                }
                house.getPostulates().addAll(postulates);
            });
            return houseRepo.save(house);
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
                // 狀態 (擁有者不更動)
                Optional.ofNullable(houseDTO.getReview()).ifPresent(modify::setReview);
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

    // 查詢全部 (包含分數)
    @SuppressWarnings("rawtypes")
    public Page<Map<String, Object>> findAllWithScores(HouseDTO houseDTO) {
        // 1. 分頁查詢 House 資料
        Integer page = Optional.ofNullable(houseDTO.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
        Integer limit = Optional.ofNullable(houseDTO.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
        Boolean dir = Optional.ofNullable(houseDTO.getDir()).orElse(false);
        String order = Optional.ofNullable(houseDTO.getOrder()).orElse(null);

        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        PageRequest pageRequest = PageRequest.of(page, limit, sort);

        Page<House> housePage = houseRepo.findAll(pageRequest);

        // 2. 取得所有 House 的 ID
        List<UUID> houseIds = housePage.stream()
                .map(House::getId)
                .collect(Collectors.toList());

        // 3. 批量查詢 MongoDB 中這些 houseId 的 score 和評分數量，排除 score 為 0 的資料
        MatchOperation matchHouseIds = Aggregation.match(Criteria.where("houseId").in(houseIds));
        MatchOperation excludeZeroScores = Aggregation.match(Criteria.where("score").gt(0)); // 排除 score 為 0 的資料
        GroupOperation groupByHouse = Aggregation.group("houseId")
                .avg("score").as("averageScore")
                .count().as("totalScores"); // 計算每個 house 的總評分數量
        Aggregation aggregation = Aggregation.newAggregation(matchHouseIds, excludeZeroScores, groupByHouse);

        // Specify types for the AggregationResults
        AggregationResults<Map> mongoResults = mongoTemplate.aggregate(aggregation, HouseMongo.class, Map.class);

        // 4. 將 MongoDB 的結果轉換為 Map 方便後續查詢
        Map<UUID, Map<String, Object>> houseScores = mongoResults.getMappedResults().stream()
                .collect(Collectors.toMap(
                        result -> (UUID) result.get("_id"), // houseId
                        result -> {
                            Map<String, Object> scoreData = new HashMap<>();
                            scoreData.put("averageScore", result.get("averageScore"));
                            scoreData.put("totalScores", ((Number) result.get("totalScores")).longValue()); // Cast to
                                                                                                            // Long
                            return scoreData;
                        }));

        // 5. 合併 House 資料和 MongoDB 中的 Score 和總評分數量
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (House house : housePage.getContent()) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("houseId", house.getId());
            resultMap.put("houseDetails", house);

            // 如果有 MongoDB 中的分數和總評分數量則加入，否則設置為預設值
            Map<String, Object> scoreData = houseScores.getOrDefault(house.getId(), new HashMap<>());
            Double averageScore = (Double) scoreData.getOrDefault("averageScore", 0.0);
            Long totalScores = (Long) scoreData.getOrDefault("totalScores", 0L); // This is now safely cast

            resultMap.put("averageScore", averageScore);
            resultMap.put("totalScores", totalScores); // 加入總評分數量

            resultList.add(resultMap);
        }

        // 6. 返回分頁結果
        return new PageImpl<>(resultList, pageRequest, housePage.getTotalElements());
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

    // 條件查詢 (包含分數)
    @SuppressWarnings("rawtypes")
    public Page<Map<String, Object>> findWithScores(HouseDTO houseDTO) {
        System.out.println(System.currentTimeMillis() + "***************SERVICE START*********");
        // 1. 頁數、限制和排序參數
        Integer page = Optional.ofNullable(houseDTO.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
        Integer limit = Optional.ofNullable(houseDTO.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
        Boolean dir = Optional.ofNullable(houseDTO.getDir()).orElse(false);
        String order = Optional.ofNullable(houseDTO.getOrder()).orElse(null);

        // 是否有排序條件
        Sort sort = (order != null) ? Sort.by(dir ? Sort.Direction.DESC : Sort.Direction.ASC, order) : Sort.unsorted();

        System.out.println(System.currentTimeMillis() + "***************22222222222222*********"
                + System.currentTimeMillis() % 100000);
        // 2. 查詢符合條件的 House 資料
        PageRequest pageRequest = PageRequest.of(page, limit, sort);
        Page<House> housePage = houseRepo.findAll(HouseSpecification.filterHouses(houseDTO), pageRequest);

        System.out.println(System.currentTimeMillis() + "***************333333333333*********"
                + System.currentTimeMillis() % 100000);
        // 3. 取得所有查詢結果中的 houseId
        List<UUID> houseIds = housePage.stream()
                .map(House::getId)
                .collect(Collectors.toList());

        if (houseIds.isEmpty()) {
            return Page.empty();
        }

        System.out.println(
                System.currentTimeMillis() + "***************44444444*********" + System.currentTimeMillis() % 100000);
        // 4. 批量查詢 MongoDB 中這些 houseId 的 score 和評分數量，排除 score 為 0 的資料
        MatchOperation matchHouseIds = Aggregation.match(Criteria.where("houseId").in(houseIds));
        MatchOperation excludeZeroScores = Aggregation.match(Criteria.where("score").gt(0)); // 排除 score 為 0 的資料
        GroupOperation groupByHouse = Aggregation.group("houseId")
                .avg("score").as("averageScore")
                .count().as("totalScores"); // 計算每個 house 的總評分數量
        Aggregation aggregation = Aggregation.newAggregation(matchHouseIds, excludeZeroScores, groupByHouse);

        // Specify types for the AggregationResults
        AggregationResults<Map> mongoResults = mongoTemplate.aggregate(aggregation, HouseMongo.class,
                Map.class);

        System.out.println(System.currentTimeMillis() + "***************55555555555555*********"
                + System.currentTimeMillis() % 100000);
        // 5. 將 MongoDB 的結果轉換為 Map，方便後續查詢
        Map<UUID, Map<String, Object>> houseScores = mongoResults.getMappedResults().stream()
                .collect(Collectors.toMap(
                        result -> (UUID) result.get("_id"), // houseId
                        result -> {
                            Map<String, Object> scoreData = new HashMap<>();
                            scoreData.put("averageScore", result.get("averageScore"));
                            scoreData.put("totalScores", ((Number) result.get("totalScores")).intValue()); // Cast to
                                                                                                           // Integer
                            return scoreData;
                        }));

        System.out.println(System.currentTimeMillis() + "***************666666666666*********"
                + System.currentTimeMillis() % 100000);
        // 6. 合併 House 資料和 MongoDB 中的 Score
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (House house : housePage.getContent()) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("houseId", house.getId());
            resultMap.put("houseDetails", house);

            // 如果有 MongoDB 中的分數和評分數量則加入，否則設置為預設值
            Map<String, Object> scoreData = houseScores.getOrDefault(house.getId(), new HashMap<>());
            Double averageScore = (Double) scoreData.getOrDefault("averageScore", 0.0);
            Integer totalScores = (Integer) scoreData.getOrDefault("totalScores", 0); // Change to Integer

            resultMap.put("averageScore", averageScore);
            resultMap.put("totalScores", totalScores); // 加入總評分數量

            resultList.add(resultMap);
        }

        System.out.println(System.currentTimeMillis() + "***************SERVICE END*********"
                + System.currentTimeMillis() % 100000);
        // 7. 返回合併後的分頁結果
        return new PageImpl<>(resultList, pageRequest, housePage.getTotalElements());
    }

    /**
     * 根據 userId，計算該使用者所有房源的總數、審核狀態和上架狀態
     * 
     * @param userId User 的 UUID
     * @return 包含統計結果的 Map
     */
    public Map<String, Long> getHouseStatisticsByUserId(UUID userId) {
        Map<String, Long> statistics = new HashMap<>();
        try {
            Object[] results = houseRepo.getHouseStatisticsByUserId(userId);

            if (results == null || results.length == 0) {
                logger.warn("No results returned for user ID: {}", userId);
                return statistics;
            }

            if (results.length == 1 && results[0] instanceof Object[]) {
                // If the result is a nested array, use the nested array
                results = (Object[]) results[0];
            }

            String[] keys = { "totalHouses", "reviewNull", "reviewFalse", "reviewTrue", "showTrue", "showFalse" };

            for (int i = 0; i < keys.length; i++) {
                if (i < results.length) {
                    statistics.put(keys[i], convertToLong(results[i]));
                } else {
                    statistics.put(keys[i], 0L);
                    logger.warn("Missing data for '{}' for user ID: {}", keys[i], userId);
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching house statistics for user ID: {}", userId, e);
        }
        return statistics;
    }

    private Long convertToLong(Object value) {
        if (value == null) {
            return 0L;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            if (array.length > 0) {
                return convertToLong(array[0]);
            }
        }
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            logger.warn("Failed to convert value to Long: {}", value);
            return 0L;
        }
    }

}
