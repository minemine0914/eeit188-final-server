package com.ispan.eeit188_final.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.dto.HouseMongoDTO;
import com.ispan.eeit188_final.model.HouseMongo;
import com.ispan.eeit188_final.repository.HouseMongoRepository;

@Service
public class HouseMongoService {

	private static final Integer PAGEABLE_DEFAULT_PAGE = 0;
	private static final Integer PAGEABLE_DEFAULT_LIMIT = 10;

	@Autowired
	private HouseMongoRepository houseMongoRepository;

	public List<HouseMongo> findAll() {
		return houseMongoRepository.findAll();
	}

	public Page<HouseMongo> findAll(HouseMongoDTO houseMongoDTO) {
		// 頁數 限制 排序
		Integer page = Optional.ofNullable(houseMongoDTO.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
		Integer limit = Optional.ofNullable(houseMongoDTO.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
		Boolean dir = Optional.ofNullable(houseMongoDTO.getDir()).orElse(false);
		String order = Optional.ofNullable(houseMongoDTO.getOrder()).orElse(null);
		// 是否排序
		Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
		return houseMongoRepository.findAll(PageRequest.of(page, limit, sort));
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
		System.out.println("1");
		if (houseMongo != null && houseMongo.getUserId() != null && houseMongo.getHouseId() != null) {
			System.out.println("2");
			HouseMongo dbHouseMongo = houseMongoRepository.findByUserIdAndHouseId(houseMongo.getUserId(),
					houseMongo.getHouseId());
			if (dbHouseMongo == null) {
				System.out.println("3");
				return houseMongoRepository.save(houseMongo);
			}
		}
		return null;
	}

	public HouseMongo update(HouseMongo houseMongo) {
		return houseMongoRepository.save(houseMongo);
	}

	public void deleteById(UUID id) {
		houseMongoRepository.deleteById(id);
	}

	// 設為愛心 & 取消愛心
	public HouseMongo likeHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null && houseMongoDto.getUserId().equals(dbHouse.getUserId())
					&& houseMongoDto.getHouseId().equals(dbHouse.getHouseId())) {
				// 先判定更新前狀態，再更新(順序不可顛倒)
				dbHouse.setLikeDate(dbHouse.getLiked() == false ? new Date() : null);
				dbHouse.setLiked(!dbHouse.getLiked());
				return houseMongoRepository.save(dbHouse);
			}
		}
		return null;
	}

	// 評分
	public HouseMongo rateHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null && houseMongoDto.getUserId().equals(dbHouse.getUserId())
					&& houseMongoDto.getHouseId().equals(dbHouse.getHouseId())) {
				if (houseMongoDto.getScore() >= 1 && houseMongoDto.getScore() <= 5) {
					dbHouse.setScoreDate(dbHouse.getScore() == 0 ? new Date() : new Date(dbHouse.getScore()));
					dbHouse.setScore(houseMongoDto.getScore());
					return houseMongoRepository.save(dbHouse);
				}
			}
		}
		return null;
	}

	// 判斷User是否對House點過查詢(???時間內(永遠)不重複計算)
	public HouseMongo clickHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null && houseMongoDto.getUserId().equals(dbHouse.getUserId())
					&& houseMongoDto.getHouseId().equals(dbHouse.getHouseId())) {
				if (!dbHouse.getClicked()) {
					dbHouse.setClicked(true);
					dbHouse.setClickDate(new Date());
				}
				return houseMongoRepository.save(dbHouse);
			}
		}
		return null;
	}

	// 判斷User是否對House點過分享(永遠不重複計算)
	public HouseMongo shareHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null && houseMongoDto.getUserId().equals(dbHouse.getUserId())
					&& houseMongoDto.getHouseId().equals(dbHouse.getHouseId())) {
				if (!dbHouse.getShared()) {
					dbHouse.setShared(true);
					dbHouse.setShareDate(new Date());
				}
				return houseMongoRepository.save(dbHouse);
			}
		}
		return null;
	}

	// 重置like(=false)
	public HouseMongo resetLikeHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null && houseMongoDto.getUserId().equals(dbHouse.getUserId())
					&& houseMongoDto.getHouseId().equals(dbHouse.getHouseId())) {
				dbHouse.setLiked(false);
				dbHouse.setLikeDate(null);
				return houseMongoRepository.save(dbHouse);
			}
		}
		return null;
	}

	// 重置評分(=0)
	public HouseMongo resetRateHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null && houseMongoDto.getUserId().equals(dbHouse.getUserId())
					&& houseMongoDto.getHouseId().equals(dbHouse.getHouseId())) {
				dbHouse.setScoreDate(null);
				dbHouse.setScore(0);
				return houseMongoRepository.save(dbHouse);
			}
		}
		return null;
	}

	// 重置click(=false)
	public HouseMongo resetClickHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null && houseMongoDto.getUserId().equals(dbHouse.getUserId())
					&& houseMongoDto.getHouseId().equals(dbHouse.getHouseId())) {
				dbHouse.setClicked(false);
				dbHouse.setClickDate(null);
				return houseMongoRepository.save(dbHouse);
			}
		}
		return null;
	}

	// 重置share
	public HouseMongo resetShareHouse(HouseMongoDTO houseMongoDto) {
		if (houseMongoDto != null && houseMongoDto.getUserId() != null && houseMongoDto.getHouseId() != null) {
			HouseMongo dbHouse = houseMongoRepository.findByUserIdAndHouseId(houseMongoDto.getUserId(),
					houseMongoDto.getHouseId());
			if (dbHouse != null && houseMongoDto.getUserId().equals(dbHouse.getUserId())
					&& houseMongoDto.getHouseId().equals(dbHouse.getHouseId())) {
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
