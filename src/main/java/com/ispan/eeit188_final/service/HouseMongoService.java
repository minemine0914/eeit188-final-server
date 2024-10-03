package com.ispan.eeit188_final.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.dto.HouseDTO;
import com.ispan.eeit188_final.dto.HouseMongoDTO;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.HouseMongo;
import com.ispan.eeit188_final.repository.HouseMongoRepository;
import com.ispan.eeit188_final.repository.HouseRepository;
import com.ispan.eeit188_final.repository.UserRepository;

@Service
public class HouseMongoService {

	// 紀錄某User是否對某House按過: 愛心, 點擊, 分享, 評分

	private static final Integer PAGEABLE_DEFAULT_PAGE = 0;
	private static final Integer PAGEABLE_DEFAULT_LIMIT = 10;

	@Autowired
	private HouseMongoRepository houseMongoRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HouseRepository houseRepository;

	@Autowired
	private HouseService houseService;

	// 一般查詢全部
	public List<HouseMongo> findAll() {
		return houseMongoRepository.findAll();
	}

	// 分頁查詢全部(傳排序用的4個變數)
	public Page<HouseMongo> findAll(HouseMongoDTO houseMongoDTO) {
		if (houseMongoDTO != null) {
			Query query = new Query();

			Boolean sortDirection = houseMongoDTO.getDir() != null ? houseMongoDTO.getDir() : false;
			String sortField = houseMongoDTO.getOrder() != null && houseMongoDTO.getOrder().length() != 0
					? houseMongoDTO.getOrder()
					: "id";
			Integer page = houseMongoDTO.getPage() != null ? houseMongoDTO.getPage() : PAGEABLE_DEFAULT_PAGE;
			Integer size = houseMongoDTO.getLimit() != null ? houseMongoDTO.getLimit() : PAGEABLE_DEFAULT_LIMIT;
			// Add criteria if needed (this is an example, you can customize it)
			// query.addCriteria(Criteria.where("someField").is("someValue"));
			long total = mongoTemplate.count(query, HouseMongo.class);

			// Sorting
			Sort sort = sortDirection ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
			query.with(sort);
			query.with(PageRequest.of(page, size));

			// Fetching the results
			List<HouseMongo> houseMongos = mongoTemplate.find(query, HouseMongo.class);

			return new PageImpl<>(houseMongos, PageRequest.of(page, size), total);
		}
		return null;
	}

	// 所有house的ID和平均分數
	public Page<Map<String, Object>> getAverageScoreGroupedByHouse(HouseMongoDTO houseMongoDTO) {
		if (houseMongoDTO != null) {
			System.out.println(System.currentTimeMillis()+"++++++++++++++++++++++SERVICE START++++++++++++++++++"+System.currentTimeMillis()%100000);
			// 1. 先過濾掉 score 為 0 的資料
			MatchOperation excludeZeroScores = Aggregation.match(Criteria.where("score").gt(0));

			System.out.println(System.currentTimeMillis()+"+++++++++++++++++++22222222222222222+++++++++++++++++"+System.currentTimeMillis()%100000);
			// 2. 分組計算平均分數和總評分數量
			GroupOperation groupByHouse = Aggregation.group("houseId")
					.avg("score").as("averageScore")
					.count().as("totalScores"); // 計算總評分數量

			Integer randomFactor = houseMongoDTO.getRandomFactor() != null
					? Math.min(Math.max(houseMongoDTO.getRandomFactor(), 0), 100)
					: 0;

			System.out.println(System.currentTimeMillis()+"++++++++++++++++++33333333333333333++++++++++++++++"+System.currentTimeMillis()%100000);
			// 3. 投影來重新命名 houseId 並應用隨機係數
			ProjectionOperation project = Aggregation.project()
					.and("_id").as("houseId")
					.and("averageScore").as("averageScore")
					.and("totalScores").as("totalScores")
					.andExpression("averageScore + (rand() * " + randomFactor + ")").as("averageScoreModified")
					.andExclude("_id");

			System.out.println(System.currentTimeMillis()+"++++++++++++++444444444444444++++++++++++++++++"+System.currentTimeMillis()%100000);
			// 4. 組合聚合查詢管道
			Aggregation aggregation = Aggregation.newAggregation(excludeZeroScores, groupByHouse, project);

			System.out.println(System.currentTimeMillis()+"++++++++++++++5555555555555555++++++++++++++"+System.currentTimeMillis()%100000);
			// 5. 執行聚合查詢
			AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, HouseMongo.class, Map.class);
			List<Map> mappedResults = results.getMappedResults();

			System.out.println(System.currentTimeMillis()+"++++++++++++++6666666666666666++++++++++++++"+System.currentTimeMillis()%100000);
			// 6. 取得所有房屋ID，進行批量查詢房屋詳情
			List<UUID> houseIds = mappedResults.stream()
					.map(result -> (UUID) result.get("houseId"))
					.collect(Collectors.toList());

			List<House> houses = houseRepository.findAllById(houseIds);

			System.out.println(System.currentTimeMillis()+"++++++++++++++7777777777777++++++++++++++"+System.currentTimeMillis()%100000);
			// 7. 將房屋詳情轉換為快速查找的Map
			Map<UUID, House> houseMap = houses.stream()
					.collect(Collectors.toMap(House::getId, house -> house));

			System.out.println(System.currentTimeMillis()+"++++++++++++++888888888888888++++++++++++++"+System.currentTimeMillis()%100000);
			// 8. 準備結果集，並過濾和組合結果
			List<Map<String, Object>> output = new ArrayList<>();
			for (Map<String, Object> result : mappedResults) {
				UUID houseId = (UUID) result.get("houseId");
				House house = houseMap.get(houseId);
				if (house == null) {
					continue;
				}
				// 根據 userId 過濾資料
				if (houseMongoDTO.getUserId() != null && house.getUser() != null) {
					if (!houseMongoDTO.getUserId().equals(house.getUser().getId())) {
						continue;
					}
				}
				Map<String, Object> outputMap = new HashMap<>(result);
				outputMap.put("houseDetails", house);
				output.add(outputMap);
			}

			System.out.println(System.currentTimeMillis()+"++++++++++++++999999999999999++++++++++++++"+System.currentTimeMillis()%100000);
			// 9. 處理分頁
			long total = output.size();
			Integer page = houseMongoDTO.getPage() != null ? houseMongoDTO.getPage() : PAGEABLE_DEFAULT_PAGE;
			Integer size = houseMongoDTO.getLimit() != null ? houseMongoDTO.getLimit() : PAGEABLE_DEFAULT_LIMIT;

			System.out.println(System.currentTimeMillis()+"++++++++++++++TTTTTTTTTTTTTTTTTTTT++++++++++++++"+System.currentTimeMillis()%100000);
			// 10. 處理排序和分頁
			boolean sortDirection = houseMongoDTO.getDir() != null ? houseMongoDTO.getDir() : false;
			String sortField = houseMongoDTO.getOrder() != null && houseMongoDTO.getOrder().length() != 0
					? houseMongoDTO.getOrder()
					: "houseId"; // 預設排序欄位

			List<Map<String, Object>> sortedResults = output.stream()
					.sorted((a, b) -> {
						Object val1 = a.get(sortField);
						Object val2 = b.get(sortField);
						// 如果值為null，則認為它比任何非null值小
						if (val1 == null) return sortDirection ? 1 : -1;
						if (val2 == null) return sortDirection ? -1 : 1;
						// 確保值是 Comparable 類型，否則跳過排序
						if (val1 instanceof Comparable && val2 instanceof Comparable) {
							return sortDirection ? ((Comparable) val2).compareTo(val1)
									: ((Comparable) val1).compareTo(val2);
						}
						return 0;
					})
					.skip((long) page * size) // 避免乘法溢出，使用long
					.limit(size)
					.collect(Collectors.toList());

			System.out.println(System.currentTimeMillis()+"++++++++++++++SERVICE END++++++++++++++"+System.currentTimeMillis()%100000);
			// 11. 返回分頁結果
			return new PageImpl<>(sortedResults, PageRequest.of(page, size), total);
		}
		return Page.empty();
	}

	// user的所有house的ID和平均分數
	public Page<Map<String, Object>> getAverageScoreByUserHouse(HouseMongoDTO houseMongoDTO) {
		if (houseMongoDTO != null && houseMongoDTO.getUserId() != null) {
			// Step 1: Find all houses owned by the user
			HouseDTO houseDTO = HouseDTO.builder()
					.userId(houseMongoDTO.getUserId())
					.build();
			Page<House> houses = houseService.find(houseDTO);

			// Create a list to hold house IDs
			List<UUID> userHouseIds = houses.stream()
					.map(House::getId)
					.collect(Collectors.toList());

			// Step 2: Create the aggregation pipeline to calculate average scores for the
			// user's houses
			GroupOperation groupByHouse = Aggregation.group("houseId").avg("score").as("averageScore");
			MatchOperation matchUserHouses = Aggregation.match(Criteria.where("houseId").in(userHouseIds));

			// Combine the stages: match followed by group
			Aggregation aggregation = Aggregation.newAggregation(matchUserHouses, groupByHouse);

			// Step 3: Execute the aggregation to get the results
			AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, HouseMongo.class, Map.class);
			List<Map> mappedResults = results.getMappedResults();

			// Step 4: Create a map for quick lookup of average scores by houseId
			Map<UUID, Object> averageScoreMap = new HashMap<>();
			for (Map<String, Object> result : mappedResults) {
				UUID houseId = (UUID) result.get("_id");
				Object averageScore = result.get("averageScore");
				// if((Double)averageScore==0.0) {averageScore=0.0;}
				averageScoreMap.put(houseId, averageScore);
			}

			// Step 5: Build the output including all houses owned by the user
			List<Map<String, Object>> output = new ArrayList<>();
			for (House house : houses) {
				Map<String, Object> outputMap = new HashMap<>();
				UUID houseId = house.getId();
				outputMap.put("houseId", houseId);
				outputMap.put("houseDetails", house); // Include house details

				// Add average score, defaulting to -1 if not present
				outputMap.put("averageScore", averageScoreMap.getOrDefault(houseId, 0.0));
				output.add(outputMap);
			}

			// Step 6: Pagination
			long total = output.size();
			Integer page = houseMongoDTO.getPage() != null ? houseMongoDTO.getPage() : PAGEABLE_DEFAULT_PAGE;
			Integer size = houseMongoDTO.getLimit() != null ? houseMongoDTO.getLimit() : PAGEABLE_DEFAULT_LIMIT;

			// Step 7: Sort the results
			boolean sortDirection = houseMongoDTO.getDir() != null ? houseMongoDTO.getDir() : false;
			String sortField = houseMongoDTO.getOrder() != null && houseMongoDTO.getOrder().length() != 0
					? houseMongoDTO.getOrder()
					: "houseId"; // Default sort field

			List<Map<String, Object>> sortedResults = output.stream()
					.sorted((a, b) -> {
						Object val1 = a.get(sortField);
						Object val2 = b.get(sortField);
						return sortDirection ? ((Comparable) val2).compareTo(val1)
								: ((Comparable) val1).compareTo(val2);
					})
					.skip(page * size)
					.limit(size)
					.collect(Collectors.toList());

			// Return the results as a Page object
			return new PageImpl<>(sortedResults, PageRequest.of(page, size), total);
		}
		return Page.empty();
	}

	// Method to get total reviews, average score, and score counts from 1 to 5
	public Map<String, Object> getScoreDetail(UUID houseId) {
		// 匹配特定的 houseId
		MatchOperation matchHouseId = Aggregation.match(Criteria.where("houseId").is(houseId));

		// 根據 houseId 分組，以獲取總評價數量、平均分數和分數範圍的統計
		GroupOperation groupByHouse = Aggregation.group("houseId")
				.count().as("totalReviews")  // 總評價數量
				.avg("score").as("averageScore")  // 計算平均分數
				.sum(ConditionalOperators.when(Criteria.where("score").is(1)).then(1).otherwise(0)).as("scoresInRange0To1")
				.sum(ConditionalOperators.when(Criteria.where("score").is(2)).then(1).otherwise(0)).as("scoresInRange1To2")
				.sum(ConditionalOperators.when(Criteria.where("score").is(3)).then(1).otherwise(0)).as("scoresInRange2To3")
				.sum(ConditionalOperators.when(Criteria.where("score").is(4)).then(1).otherwise(0)).as("scoresInRange3To4")
				.sum(ConditionalOperators.when(Criteria.where("score").is(5)).then(1).otherwise(0)).as("scoresInRange4To5");
	
		// 組合聚合階段
		Aggregation aggregation = Aggregation.newAggregation(matchHouseId, groupByHouse);
	
		// 執行聚合
		AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, HouseMongo.class, Map.class);
	
		// 創建結果地圖以存儲統計數據
		Map<String, Object> result = new HashMap<>();
		if (!results.getMappedResults().isEmpty()) {
			Map<String, Object> scoreCounts = results.getMappedResults().get(0);
			result.put("totalReviews", scoreCounts.get("totalReviews"));
			result.put("averageScore", scoreCounts.get("averageScore"));
			result.put("scoresInRange0To1", scoreCounts.get("scoresInRange0To1"));
			result.put("scoresInRange1To2", scoreCounts.get("scoresInRange1To2"));
			result.put("scoresInRange2To3", scoreCounts.get("scoresInRange2To3"));
			result.put("scoresInRange3To4", scoreCounts.get("scoresInRange3To4"));
			result.put("scoresInRange4To5", scoreCounts.get("scoresInRange4To5"));
		} else {
			result.put("totalReviews", 0);
			result.put("averageScore", 0.0); // 默認平均分數
			result.put("scoresInRange0To1", 0);
			result.put("scoresInRange1To2", 0);
			result.put("scoresInRange2To3", 0);
			result.put("scoresInRange3To4", 0);
			result.put("scoresInRange4To5", 0);
		}
	
		return result;
	}

	public Map<String, Object> getClickCountsByHouseId(UUID userId, UUID houseId) {
		// Create the aggregation pipeline
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation
						.match(Criteria.where("userId").is(userId).and("houseId").is(houseId).and("clicked").is(true)),
				Aggregation.group("houseId").count().as("counts"));

		// Execute the aggregation
		AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, HouseMongo.class, Map.class);
		List<Map> mappedResults = results.getMappedResults();

		// Prepare the response
		if (!mappedResults.isEmpty()) {
			return Map.of("houseId", houseId.toString(), "counts", mappedResults.get(0).get("counts"));
		} else {
			return Map.of("houseId", houseId.toString(), "counts", 0); // Return 0 if no clicks
		}
	}

	public HouseMongo findById(UUID id) {
		return houseMongoRepository.findById(id).orElse(null);
	}

	public List<HouseMongo> findByUserId(UUID id) {
		List<HouseMongo> houses = houseMongoRepository.findByUserId(id);
		if (houses != null && houses.size() != 0) {
			return houses;
		}
		return null;
	}

	public List<HouseMongo> findByHouseId(UUID id) {
		List<HouseMongo> houses = houseMongoRepository.findByHouseId(id);
		if (houses != null && houses.size() != 0) {
			return houses;
		}
		return null;
	}

	public HouseMongo findByUserIdAndHouseId(UUID userId, UUID houseId) {
		HouseMongo house = houseMongoRepository.findByUserIdAndHouseId(userId, houseId);
		if (house != null) {
			return house;
		}
		return null;
	}

	public HouseMongo create(HouseMongo houseMongo) {
		if (houseMongo != null && houseMongo.getUserId() != null && houseMongo.getHouseId() != null
				&& userRepository.findById(houseMongo.getUserId()).isPresent()
				&& houseRepository.findById(houseMongo.getHouseId()).isPresent()) {

			HouseMongo dbHouseMongo = houseMongoRepository.findByUserIdAndHouseId(houseMongo.getUserId(),
					houseMongo.getHouseId());
			if (dbHouseMongo == null) {
				return houseMongoRepository.save(houseMongo);
			}
		}
		return null;
	}

	// 所有house的ID和點擊數
	public Page<Map<String, Object>> countXXAndHouseForAllHouses(HouseMongoDTO houseMongoDTO, String clickOrShare) {
		// Default values for pagination
		Integer page = houseMongoDTO.getPage() != null ? houseMongoDTO.getPage() : PAGEABLE_DEFAULT_PAGE;
		Integer size = houseMongoDTO.getLimit() != null ? houseMongoDTO.getLimit() : PAGEABLE_DEFAULT_LIMIT;

		// Match criteria for aggregation
		Criteria matchCriteria = Criteria.where(clickOrShare).is(true);

		// Create the aggregation pipeline
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.match(matchCriteria),
				Aggregation.group("houseId").count().as("counts"),
				Aggregation.sort(Sort.by(houseMongoDTO.getDir() ? Sort.Direction.DESC : Sort.Direction.ASC,
						houseMongoDTO.getOrder() != null ? houseMongoDTO.getOrder() : "counts")),
				Aggregation.skip((long) page * size),
				Aggregation.limit(size));

		// Execute the aggregation
		AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, HouseMongo.class, Map.class);
		List<Map> mappedResults = results.getMappedResults();

		// Get total count using the same match criteria
		long total = mongoTemplate.count(new Query(matchCriteria), HouseMongo.class);

		// Transform the output to include houseId and counts, and replace with house
		// details
		List<Map<String, Object>> response = new ArrayList<>();
		for (Map<String, Object> result : mappedResults) {
			UUID houseId = (UUID) result.get("_id");
			House house = houseRepository.findById(houseId).orElse(null); // Fetch one house by houseId
			if (houseMongoDTO.getIgnoreNull() != null && houseMongoDTO.getIgnoreNull() && house == null) {
				continue;
			}
			Map<String, Object> outputMap = new HashMap<>();
			outputMap.put("houseId", houseId);
			outputMap.put("counts", result.get("counts"));
			outputMap.put("houseDetails", house); // Include house details

			response.add(outputMap);
		}

		return new PageImpl<>(response, PageRequest.of(page, size), total);
	}

	// 所有house的ID和分享數
	public Page<Map<String, Object>> countXXForAllHouses(HouseMongoDTO houseMongoDTO, String clickOrShare) {
		// Default values for pagination
		Integer page = houseMongoDTO.getPage() != null ? houseMongoDTO.getPage() : PAGEABLE_DEFAULT_PAGE;
		Integer size = houseMongoDTO.getLimit() != null ? houseMongoDTO.getLimit() : PAGEABLE_DEFAULT_LIMIT;

		// Match criteria for aggregation
		Criteria matchCriteria = Criteria.where(clickOrShare).is(true);

		// Create the aggregation pipeline
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.match(matchCriteria),
				Aggregation.group("houseId").count().as("counts"),
				Aggregation.sort(Sort.by(houseMongoDTO.getDir() ? Sort.Direction.DESC : Sort.Direction.ASC,
						houseMongoDTO.getOrder() != null ? houseMongoDTO.getOrder() : "counts")),
				Aggregation.skip((long) page * size),
				Aggregation.limit(size));

		// Execute the aggregation
		AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, HouseMongo.class, Map.class);
		List<Map> mappedResults = results.getMappedResults();

		// Get total count using the same match criteria
		long total = mongoTemplate.count(new Query(matchCriteria), HouseMongo.class);

		// Transform the output to include houseId and counts
		List<Map<String, Object>> response = mappedResults.stream()
				.map(result -> Map.of("houseId", result.get("_id"), "counts", result.get("counts")))
				.collect(Collectors.toList());

		return new PageImpl<>(response, PageRequest.of(page, size), total);
	}

	public HouseMongo update(HouseMongo houseMongo) {
		if (houseMongo != null && houseMongo.getUserId() != null && houseMongo.getHouseId() != null) {
			HouseMongo dbData = findByUserIdAndHouseId(houseMongo.getUserId(), houseMongo.getHouseId());
			if (dbData != null) {
				if (!dbData.getClicked() && houseMongo.getClicked() != null && houseMongo.getClicked()) {
					dbData.setClicked(houseMongo.getClicked());
					dbData.setClickDate(new Date());
				}
				if (!dbData.getLiked() && houseMongo.getLiked() != null && houseMongo.getLiked()) {
					dbData.setLiked(houseMongo.getLiked());
					dbData.setLikeDate(new Date());
				}
				if (!dbData.getShared() && houseMongo.getShared() != null && houseMongo.getShared()) {
					dbData.setShared(houseMongo.getShared());
					dbData.setShareDate(new Date());
				}
				if (dbData.getScore() == 0 && houseMongo.getScore() != null) {
					dbData.setScore(houseMongo.getScore());
					dbData.setScoreDate(new Date());
				}
				return dbData;
			}
			return houseMongoRepository.save(houseMongo);
		}
		return null;
	}

	public void deleteById(UUID id) {
		houseMongoRepository.deleteById(id);
	}

	// 回傳該House的愛心總數
	public long countLikesForHouse(UUID houseId) {
		return houseMongoRepository.countByHouseIdAndLikedTrue(houseId);
	}

	// 回傳該House的被點擊總數
	public long countClicksForHouse(UUID houseId) {
		return houseMongoRepository.countByHouseIdAndClickedTrue(houseId);
	}

	// 回傳該House的被分享總數
	public long countSharesForHouse(UUID houseId) {
		return houseMongoRepository.countByHouseIdAndSharedTrue(houseId);
	}

	// 設為愛心 & 取消愛心
	public HouseMongo likeHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null
				&& userRepository.findById(houseMongoDto.getUserId()).isPresent()
				&& houseRepository.findById(houseMongoDto.getHouseId()).isPresent()) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());

			if (dbHouse == null) {
				dbHouse = new HouseMongo();
				dbHouse.setUserId(houseMongoDto.getUserId());
				dbHouse.setHouseId(houseMongoDto.getHouseId());
			}

			// 先判定更新前狀態，再更新(順序不可顛倒)
			dbHouse.setLikeDate(dbHouse.getLiked() == false ? new Date() : null);
			dbHouse.setLiked(!dbHouse.getLiked());

			return houseMongoRepository.save(dbHouse);
		}
		return null;
	}

	// 評分
	public HouseMongo rateHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null
				&& userRepository.findById(houseMongoDto.getUserId()).isPresent()
				&& houseRepository.findById(houseMongoDto.getHouseId()).isPresent()) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());

			if (dbHouse == null) {
				dbHouse = new HouseMongo();
				dbHouse.setUserId(houseMongoDto.getUserId());
				dbHouse.setHouseId(houseMongoDto.getHouseId());
			}

			if (houseMongoDto.getScore() >= 1 && houseMongoDto.getScore() <= 5) {
				dbHouse.setScoreDate(
						dbHouse.getScore() == 0 || dbHouse.getScore() != houseMongoDto.getScore() ? new Date()
								: dbHouse.getScoreDate());
				dbHouse.setScore(houseMongoDto.getScore());
			}

			return houseMongoRepository.save(dbHouse);
		}
		return null;
	}

	// 判斷User是否對House點過查詢(???時間內(永遠)不重複計算)
	public HouseMongo clickHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null
				&& userRepository.findById(houseMongoDto.getUserId()).isPresent()
				&& houseRepository.findById(houseMongoDto.getHouseId()).isPresent()) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());

			if (dbHouse == null) {
				dbHouse = new HouseMongo();
				dbHouse.setUserId(houseMongoDto.getUserId());
				dbHouse.setHouseId(houseMongoDto.getHouseId());
			}

			if (!dbHouse.getClicked()) {
				dbHouse.setClicked(true);
				dbHouse.setClickDate(new Date());
			}

			return houseMongoRepository.save(dbHouse);
		}
		return null;
	}

	// 判斷User是否對House點過分享(永遠不重複計算)
	public HouseMongo shareHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null
				&& userRepository.findById(houseMongoDto.getUserId()).isPresent()
				&& houseRepository.findById(houseMongoDto.getHouseId()).isPresent()) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());

			if (dbHouse == null) {
				dbHouse = new HouseMongo();
				dbHouse.setUserId(houseMongoDto.getUserId());
				dbHouse.setHouseId(houseMongoDto.getHouseId());
			}

			if (!dbHouse.getShared()) {
				dbHouse.setShared(true);
				dbHouse.setShareDate(new Date());
			}

			return houseMongoRepository.save(dbHouse);
		}
		return null;
	}

	// 重置like(=false)
	public HouseMongo resetLikeHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null
				&& userRepository.findById(houseMongoDto.getUserId()).isPresent()
				&& houseRepository.findById(houseMongoDto.getHouseId()).isPresent()) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null) {
				dbHouse.setLiked(false);
				dbHouse.setLikeDate(null);
				return houseMongoRepository.save(dbHouse);
			}
		}
		return null;
	}

	// 重置評分(=0)
	public HouseMongo resetRateHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null
				&& userRepository.findById(houseMongoDto.getUserId()).isPresent()
				&& houseRepository.findById(houseMongoDto.getHouseId()).isPresent()) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null) {
				dbHouse.setScoreDate(null);
				dbHouse.setScore(0);
				return houseMongoRepository.save(dbHouse);
			}
		}
		return null;
	}

	// 重置click(=false)
	public HouseMongo resetClickHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null
				&& userRepository.findById(houseMongoDto.getUserId()).isPresent()
				&& houseRepository.findById(houseMongoDto.getHouseId()).isPresent()) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null) {
				dbHouse.setClicked(false);
				dbHouse.setClickDate(null);
				return houseMongoRepository.save(dbHouse);
			}
		}
		return null;
	}

	// 重置share
	public HouseMongo resetShareHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null
				&& userRepository.findById(houseMongoDto.getUserId()).isPresent()
				&& houseRepository.findById(houseMongoDto.getHouseId()).isPresent()) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null) {
				dbHouse.setShared(false);
				dbHouse.setShareDate(null);
				return houseMongoRepository.save(dbHouse);
			}
		}
		return null;
	}

	// 評分功能(1~5分別計算數量)
	// @GetMapping("/{id}/{rating}")
	// public HouseMongo getRating(@PathVariable(name = "id") UUID id,
	// @PathVariable(name = "rating") Integer rating) {
	// HouseMongo cc = houseMongoRepository.findById(id).orElse(new HouseMongo());
	// switch (rating) {
	// case 1:
	// cc.getScore()[0]++;
	// cc.setClick(cc.getClick() + 1);
	// break;
	// case 2:
	// cc.getScore()[1]++;
	// cc.setClick(cc.getClick() + 1);
	// break;
	// case 3:
	// cc.getScore()[2]++;
	// cc.setClick(cc.getClick() + 1);
	//
	// break;
	// case 4:
	// cc.getScore()[3]++;
	// cc.setClick(cc.getClick() + 1);
	// break;
	// case 5:
	// cc.getScore()[4]++;
	// cc.setClick(cc.getClick() + 1);
	// break;
	// }
	// houseMongoRepository.save(cc);
	//
	// return houseMongoRepository.findById(id).orElse(new HouseMongo());
	// }
}
