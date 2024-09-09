package com.ispan.eeit188_final.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.HouseMongo;
import com.ispan.eeit188_final.repository.HouseMongoRepository;

@Service
public class HouseMongoService {
	@Autowired
	private HouseMongoRepository houseMongoRepository;

	public List<HouseMongo> findAll() {
		return houseMongoRepository.findAll();
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

//	public List<HouseMongo> findByUserIdAndHouseId(UUID userId, UUID houseId) {
//		List<HouseMongo> houses = houseMongoRepository.findByUserIdAndHouseId(userId, houseId);
//		if (houses != null && houses.size() != 0) {
//			return houses;
//		}
//		return null;
//	}

	public HouseMongo update(HouseMongo houseMongo) {
		return houseMongoRepository.save(houseMongo);
	}

	public void deleteHouseById(UUID id) {
		houseMongoRepository.deleteById(id);
	}

	// 設為愛心 & 取消愛心
//	public HouseMongo likeHouse(UUID currentUser, UUID currentHouse) {
//		List<HouseMongo> house = houseMongoRepository.findByUserIdAndHouseId(currentUser, currentHouse);
//		if (house != null && house.size() != 0) {
//			house.setLiked(!house.getLiked());
//			return houseMongoRepository.save(house);
//		}
//		return null;
//	}

	// 判斷User是否對House點過查詢(???時間內(永遠)不重複計算)
//	public HouseMongo clickHouse(UUID currentUser, UUID currentHouse) {
//		HouseMongo house = houseMongoRepository.findById(id).orElse(null);
//		if (house != null && currentUser != null && currentHouse != null && currentUser.equals(house.getUserId())
//				&& currentHouse.equals(house.getHouseId()) && !house.getClicked()) {
//			house.setClicked(true);
//			house.setClickDate(new Date());
//			return houseMongoRepository.save(house);
//		}
//		return null;
//	}

	// 判斷User是否對House點過分享(永遠不重複計算)
	public HouseMongo shareHouse(UUID id, UUID currentUser, UUID currentHouse) {
		HouseMongo house = houseMongoRepository.findById(id).orElse(null);
		if (house != null && currentUser != null && currentHouse != null && currentUser.equals(house.getUserId())
				&& currentHouse.equals(house.getHouseId()) && !house.getShared()) {
			house.setShared(true);
			return houseMongoRepository.save(house);
		}
		return null;
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
