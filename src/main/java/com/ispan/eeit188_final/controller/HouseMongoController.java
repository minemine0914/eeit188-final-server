package com.ispan.eeit188_final.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.eeit188_final.model.HouseMongo;
import com.ispan.eeit188_final.service.HouseMongoService;

@RestController
@RequestMapping("/house/mongo")
public class HouseMongoController {
	@Autowired
	private HouseMongoService houseMongoService;

	@GetMapping("")
	public List<HouseMongo> getAll() {
		return houseMongoService.findAll();
	}

	@GetMapping("/{id}")
	public HouseMongo findHouseById(@PathVariable UUID id) {
		return houseMongoService.findById(id);
	}

	@PostMapping("")
	public HouseMongo Update(@RequestBody HouseMongo houseMongo) {
		return houseMongoService.update(houseMongo);
	}

	@DeleteMapping("/{id}")
	public void deleteHouseById(@PathVariable UUID id) {
		houseMongoService.deleteHouseById(id);
	}

	@GetMapping("/{userId}/{houseId}/like")
	public HouseMongo likeHouse(@PathVariable(name = "userId") UUID userId, @PathVariable(name = "houseId") UUID houseId) {
		return houseMongoService.likeHouse(id);
	}

	@GetMapping("/{id}/click")
	public HouseMongo clickHouse(@PathVariable(name = "id") UUID id) {
		return houseMongoService.clickHouse(id);
	}

	@GetMapping("/{id}/share")
	public HouseMongo shareHouse(@PathVariable(name = "id") UUID id) {
		return houseMongoService.shareHouse(id);
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
