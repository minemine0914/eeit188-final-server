package com.ispan.eeit188_final.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.HousePostulate;
import com.ispan.eeit188_final.model.composite.HousePostulateId;
import com.ispan.eeit188_final.repository.HousePostulateRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HousePostulateService {

	@Autowired
	private HousePostulateRepository housePostulateRepository;

	public HousePostulate findById(HousePostulateId id) {
		if (id != null) {
			Optional<HousePostulate> optional = housePostulateRepository.findById(id);
			if (optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}

	public List<HousePostulate> findAll() {
		return housePostulateRepository.findAll();
	}

	public void deleteById(HousePostulateId id) {
		if (id != null) {
			housePostulateRepository.deleteById(id);
		}
	}

	public void deleteAll() {
		housePostulateRepository.deleteAll();
	}

	public HousePostulate create(String json) {
		// try {
		// 	JSONObject obj = new JSONObject(json);

		// 	String houseId = obj.isNull("houseId") ? null : obj.getString("houseId");
		// 	String postulateId = obj.isNull("postulateId") ? null : obj.getString("postulateId");

		// 	if (houseId != null && postulateId != null) {
		// 		UUID houseUuid = UUID.fromString(houseId);
		// 		UUID postulateUuid = UUID.fromString(postulateId);
		// 		Optional<House> optHouse = houseRepository.findById(houseUuid);
		// 		Optional<Postulate> optPostulate = postulateRepository.findById(postulateUuid);

		// 		if (optHouse.isPresent() && optPostulate.isPresent()) {
		// 			HousePostulateId id = new HousePostulateId();
		// 			id.setHouseId(houseUuid);
		// 			id.setPostulateId(postulateUuid);

		// 			HousePostulate housePostulate = new HousePostulate();
		// 			housePostulate.setId(id);
		// 			housePostulate.setHouse(optHouse.get());
		// 			housePostulate.setPostulate(optPostulate.get());
		// 			return housePostulateRepository.save(housePostulate);
		// 		} else {
		// 			System.out.println("missing db entity");
		// 		}
		// 	} else {
		// 		System.out.println("missing entity id");
		// 	}
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// }
		return null;
	}

}
