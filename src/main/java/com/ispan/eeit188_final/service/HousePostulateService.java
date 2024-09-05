package com.ispan.eeit188_final.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.Cart;
import com.ispan.eeit188_final.model.HousePostulate;
import com.ispan.eeit188_final.model.composite.CartId;
import com.ispan.eeit188_final.model.composite.HousePostulateId;
import com.ispan.eeit188_final.repository.CartRepository;
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
		try {
			JSONObject obj = new JSONObject(json);

			// ==============wait for FK settings=============
//			Integer id = obj.isNull("id") ? null : obj.getInt("id");
//
//			if (id != null) {
//				Optional<Cart> optional = housePostulateRepository.findById(id);
//				if (optional.isEmpty()) {
//					Cart insert = new Cart().setId(id);
//
//					return housePostulateRepository.save(insert);
//				}
//			}
			//=====================================================
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
