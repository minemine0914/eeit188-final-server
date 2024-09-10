package com.ispan.eeit188_final.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

	@GetMapping("/")
	public List<HouseMongo> findAll() {
		return houseMongoService.findAll();
	}

	@PostMapping("/page")
	public Page<HouseMongo> findAll(HouseMongoDTO houseMongoDTO) {
		return houseMongoService.findAll(houseMongoDTO);
	}
	
	@GetMapping("/{id}")
	public HouseMongo findById(@PathVariable UUID id) {
		return houseMongoService.findById(id);
	}

	@PostMapping("/")
	public HouseMongo create(@RequestBody HouseMongo houseMongo) {
		return houseMongoService.create(houseMongo);
	}
	
	@PutMapping("/")
	public HouseMongo Update(@RequestBody HouseMongo houseMongo) {
		return houseMongoService.update(houseMongo);
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable UUID id) {
		houseMongoService.deleteById(id);
	}

	@PostMapping("/like")
	public HouseMongo likeHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.likeHouse(houseMongoDto);
	}

	@PostMapping("/rate")
	public HouseMongo rateHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.rateHouse(houseMongoDto);
	}
	
	@PostMapping("/click")
	public HouseMongo clickHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.clickHouse(houseMongoDto);
	}

	@PostMapping("/share")
	public HouseMongo shareHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.shareHouse(houseMongoDto);
	}

	@PostMapping("/reset/like")
	public HouseMongo resetLikeHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.resetLikeHouse(houseMongoDto);
	}

	@PostMapping("/reset/rate")
	public HouseMongo resetRateHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.resetRateHouse(houseMongoDto);
	}
	
	@PostMapping("/reset/click")
	public HouseMongo resetClickHouse(@RequestBody HouseMongoDTO houseMongoDto) {
		return houseMongoService.resetClickHouse(houseMongoDto);
	}

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
