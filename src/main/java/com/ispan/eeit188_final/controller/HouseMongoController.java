package com.ispan.eeit188_final.controller;

import java.util.List;
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
import com.ispan.eeit188_final.model.Ticket;
import com.ispan.eeit188_final.service.HouseMongoService;

@RestController
@CrossOrigin
@RequestMapping("/house/mongo")
public class HouseMongoController {
	
	@Autowired
	private HouseMongoService houseMongoService;

	//紀錄某User是否對某House按過: 愛心, 點擊, 分享, 評分
	
	//一般查詢全部
	@GetMapping("/")
	public List<HouseMongo> findAll() {
		return houseMongoService.findAll();
	}

	//複雜條件查詢
	@PostMapping("/page")
	public Page<HouseMongo> findAll(HouseMongoDTO houseMongoDTO) {
		return houseMongoService.findAll(houseMongoDTO);
	}
	
	//依資料ID查詢
	@GetMapping("/{id}")
	public HouseMongo findById(@PathVariable UUID id) {
		return houseMongoService.findById(id);
	}

	//新增
	@PostMapping("/")
	public HouseMongo create(@RequestBody HouseMongo houseMongo) {
		return houseMongoService.create(houseMongo);
	}
	
	//修改
	@PutMapping("/")
	public HouseMongo Update(@RequestBody HouseMongo houseMongo) {
		return houseMongoService.update(houseMongo);
	}

	//刪除
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable UUID id) {
		houseMongoService.deleteById(id);
	}

	//計算愛心數
	@PostMapping("/count/like")
	public long countLikesForHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getHouseId() != null) {
			UUID houseId = houseMongoDto.getHouseId();
			return houseMongoService.countLikesForHouse(houseId);
		}
		return 0;
	}
	
	//計算點擊數
	@PostMapping("/count/click")
	public long countClicksForHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getHouseId() != null) {
			UUID houseId = houseMongoDto.getHouseId();
			return houseMongoService.countClicksForHouse(houseId);
		}
		return 0;
	}
	
	//計算分享次數
	@PostMapping("/count/share")
	public long countSharesForHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getHouseId() != null) {
			UUID houseId = houseMongoDto.getHouseId();
			return houseMongoService.countSharesForHouse(houseId);
		}
		return 0;
	}
	
	//設為愛心
	@PostMapping("/like")
	public ResponseEntity<HouseMongo> likeHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
    		HouseMongo like = houseMongoService.likeHouse(houseMongoDto);
    			return ResponseEntity.ok(like);
    		}
    	return ResponseEntity.noContent().build();
	}

	//評分
	@PostMapping("/rate")
	public ResponseEntity<HouseMongo> rateHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
    		HouseMongo rate = houseMongoService.rateHouse(houseMongoDto);
    			return ResponseEntity.ok(rate);
    		}
    	return ResponseEntity.noContent().build();
	}
	
	//點擊+1
	@PostMapping("/click")
	public ResponseEntity<HouseMongo> clickHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
    		HouseMongo click = houseMongoService.clickHouse(houseMongoDto);
    			return ResponseEntity.ok(click);
    		}
    	
    	return ResponseEntity.noContent().build();
	}

	//分享+1
	@PostMapping("/share")
	public ResponseEntity<HouseMongo> shareHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
    		HouseMongo share = houseMongoService.shareHouse(houseMongoDto);
    			return ResponseEntity.ok(share);
    		}
    	
    	return ResponseEntity.noContent().build();
	}

	//重置愛心(設為沒按愛心)
	@PostMapping("/reset/like")
	public HouseMongo resetLikeHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.resetLikeHouse(houseMongoDto);
	}

	//重置評分(設為未評分)
	@PostMapping("/reset/rate")
	public HouseMongo resetRateHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.resetRateHouse(houseMongoDto);
	}
	
	//重置點擊(設為未點擊)
	@PostMapping("/reset/click")
	public HouseMongo resetClickHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.resetClickHouse(houseMongoDto);
	}

	//重置分享(設為未分享)
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
