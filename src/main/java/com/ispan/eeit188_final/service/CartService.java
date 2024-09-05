package com.ispan.eeit188_final.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.Cart;
import com.ispan.eeit188_final.model.composite.CartId;
import com.ispan.eeit188_final.repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	public Cart findById(CartId id) {
		if (id != null) {
			Optional<Cart> optional = cartRepository.findById(id);
			if (optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}

	public List<Cart> findAll() {
		return cartRepository.findAll();
	}

	public void deleteById(CartId id) {
		if (id != null) {
			cartRepository.deleteById(id);
		}
	}

	public void deleteAll() {
		cartRepository.deleteAll();
	}

	public Cart create(String json) {
		try {
			JSONObject obj = new JSONObject(json);

			// ==============wait for FK settings=============
//			Integer id = obj.isNull("id") ? null : obj.getInt("id");
//
//			if (id != null) {
//				Optional<Cart> optional = cartRepository.findById(id);
//				if (optional.isEmpty()) {
//					Cart insert = new Cart().setId(id);
//
//					return cartRepository.save(insert);
//				}
//			}
			//=====================================================
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
