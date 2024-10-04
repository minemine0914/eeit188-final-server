package com.ispan.eeit188_final.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.eeit188_final.dto.HouseMongoDTO;
import com.ispan.eeit188_final.model.HouseMongo;
import com.ispan.eeit188_final.service.HouseMongoService;

@RestController
@CrossOrigin
@RequestMapping("/house/mongo")
public class HouseMongoController {

	@Autowired
	private HouseMongoService houseMongoService;

	// 紀錄某User是否對某House按過: 愛心, 點擊, 分享, 評分

	// 一般查詢全部
	@GetMapping("/")
	public List<HouseMongo> findAll() {
		return houseMongoService.findAll();
	}

	// 分頁查詢全部(傳排序用的4個變數)
	@PostMapping("/page")
	public Page<HouseMongo> findAll(@RequestBody HouseMongoDTO houseMongoDTO) {
		return houseMongoService.findAll(houseMongoDTO);
	}

	// 依資料ID查詢
	@GetMapping("/{id}")
	public HouseMongo findById(@PathVariable UUID id) {
		return houseMongoService.findById(id);
	}

	// 使用userId和houseId查詢
	@PostMapping("/find")
	public HouseMongo findByUserIdAndHouseId(@RequestBody HouseMongoDTO houseMongoDTO) {
		System.out.println(houseMongoDTO.getUserId());
		if (houseMongoDTO != null && houseMongoDTO.getUserId() != null && houseMongoDTO.getHouseId() != null) {
			UUID userId = houseMongoDTO.getUserId();
			UUID houseId = houseMongoDTO.getHouseId();

			return houseMongoService.findByUserIdAndHouseId(userId, houseId);
		}
		return null;
	}

	// 使用userId和houseId查詢
	@GetMapping("/find/{userId}/{houseId}")
	public HouseMongo findByUserIdAndHouseId(@PathVariable UUID userId, @PathVariable UUID houseId) {
		return houseMongoService.findByUserIdAndHouseId(userId, houseId);
	}

	// 新增
	@PostMapping("/")
	public HouseMongo create(@RequestBody HouseMongo houseMongo) {
		return houseMongoService.create(houseMongo);
	}

	// 修改
	@PutMapping("/")
	public HouseMongo Update(@RequestBody HouseMongo houseMongo) {
		return houseMongoService.update(houseMongo);
	}

	// 修改，傳入true/評分分數才改成true/評分，原本已經是true或原本有評分則不改動
	@PutMapping("/set-true")
	public HouseMongo setNewTrue(@RequestBody HouseMongo houseMongo) {
		return houseMongoService.setNewTrue(houseMongo);
	}

	// 刪除
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable UUID id) {
		houseMongoService.deleteById(id);
	}

	// 所有house的ID和平均分數
	@PostMapping("/scores/average-grouped-by-house")
	public Page<Map<String, Object>> getAverageScoresGroupedByHouse(@RequestBody HouseMongoDTO houseMongoDTO) {
		return houseMongoService.getAverageScoreGroupedByHouse(houseMongoDTO);
	}
	
	// user的所有house的ID和平均分數
	@PostMapping("/scores/average-of-user")
	public Page<Map<String, Object>> getAverageScoreByUserHouse(@RequestBody HouseMongoDTO houseMongoDTO) {
		return houseMongoService.getAverageScoreByUserHouse(houseMongoDTO);
	}

	// user的所有house的ID和平均分數
	@GetMapping("/scores/{houseId}")
	public Map<String, Object> getHouseScoreDetail(@PathVariable UUID houseId) {
		return houseMongoService.getScoreDetail(houseId);
	}
	
	// 所有house的ID和點擊數(附帶House物件)
	@PostMapping("/count/all/obj/click")
	public Page<Map<String, Object>> getClickCountsAndHouseForAllHouses(@RequestBody HouseMongoDTO houseMongoDTO) {
		return houseMongoService.countXXAndHouseForAllHouses(houseMongoDTO,"clicked");
	}

	// 所有house的ID和分享數(附帶House物件)
	@PostMapping("/count/all/obj/share")
	public Page<Map<String, Object>> getShareCountsAndHouseForAllHouses(@RequestBody HouseMongoDTO houseMongoDTO) {
		return houseMongoService.countXXAndHouseForAllHouses(houseMongoDTO,"shared");
	}

	// 所有house的ID和點擊數(附帶HouseId)
	@PostMapping("/count/all/click")
	public Page<Map<String, Object>> getClickCountsForAllHouses(@RequestBody HouseMongoDTO houseMongoDTO) {
		return houseMongoService.countXXForAllHouses(houseMongoDTO,"clicked");
	}

	// 所有house的ID和分享數(附帶HouseId)
	@PostMapping("/count/all/share")
	public Page<Map<String, Object>> getShareCountsForAllHouses(@RequestBody HouseMongoDTO houseMongoDTO) {
		return houseMongoService.countXXForAllHouses(houseMongoDTO,"shared");
	}

	// 計算愛心數
	@PostMapping("/count/like")
	public long countLikesForHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getHouseId() != null) {
			UUID houseId = houseMongoDto.getHouseId();
			return houseMongoService.countLikesForHouse(houseId);
		}
		return 0;
	}

	// 計算點擊數
	@PostMapping("/count/click")
	public long countClicksForHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getHouseId() != null) {
			UUID houseId = houseMongoDto.getHouseId();
			return houseMongoService.countClicksForHouse(houseId);
		}
		return 0;
	}

	// 計算分享次數
	@PostMapping("/count/share")
	public long countSharesForHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getHouseId() != null) {
			UUID houseId = houseMongoDto.getHouseId();
			return houseMongoService.countSharesForHouse(houseId);
		}
		return 0;
	}

	// 設為愛心
	@PostMapping("/like")
	public ResponseEntity<HouseMongo> likeHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo like = houseMongoService.likeHouse(houseMongoDto);
			return ResponseEntity.ok(like);
		}
		return ResponseEntity.noContent().build();
	}

	// 評分
	@PostMapping("/rate")
	public ResponseEntity<HouseMongo> rateHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo rate = houseMongoService.rateHouse(houseMongoDto);
			return ResponseEntity.ok(rate);
		}
		return ResponseEntity.noContent().build();
	}

	// 點擊+1
	@PostMapping("/click")
	public ResponseEntity<HouseMongo> clickHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo click = houseMongoService.clickHouse(houseMongoDto);
			return ResponseEntity.ok(click);
		}

		return ResponseEntity.noContent().build();
	}

	// 分享+1
	@PostMapping("/share")
	public ResponseEntity<HouseMongo> shareHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo share = houseMongoService.shareHouse(houseMongoDto);
			return ResponseEntity.ok(share);
		}

		return ResponseEntity.noContent().build();
	}

	// 重置愛心(設為沒按愛心)
	@PostMapping("/reset/like")
	public HouseMongo resetLikeHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.resetLikeHouse(houseMongoDto);
	}

	// 重置評分(設為未評分)
	@PostMapping("/reset/rate")
	public HouseMongo resetRateHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.resetRateHouse(houseMongoDto);
	}

	// 重置點擊(設為未點擊)
	@PostMapping("/reset/click")
	public HouseMongo resetClickHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.resetClickHouse(houseMongoDto);
	}

	// 重置分享(設為未分享)
	@PostMapping("/reset/share")
	public HouseMongo resetShareHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.resetShareHouse(houseMongoDto);
	}

//	@GetMapping("/{id}/{rating}")
//	public HouseMongo getRating(@PathVariable(name = "id") UUID id, @PathVariable(name = "rating") Integer rating) {
//		HouseMongo cc = houseMongoRepository.findById(id).orElse(new HouseMongo());
//		switch (rating) {
//		case 1:
//			cc.getScore()[0]++;
//			cc.setClick(cc.getClick() + 1);
//			break;
//		case 2:
//			cc.getScore()[1]++;
//			cc.setClick(cc.getClick() + 1);
//			break;
//		case 3:
//			cc.getScore()[2]++;
//			cc.setClick(cc.getClick() + 1);
//
//			break;
//		case 4:
//			cc.getScore()[3]++;
//			cc.setClick(cc.getClick() + 1);
//			break;
//		case 5:
//			cc.getScore()[4]++;
//			cc.setClick(cc.getClick() + 1);
//			break;
//		}
//		houseMongoRepository.save(cc);
//
//		return houseMongoRepository.findById(id).orElse(new HouseMongo());
//	}
}
