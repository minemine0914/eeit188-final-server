package com.ispan.eeit188_final.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.Cart;
import com.ispan.eeit188_final.model.composite.CartId;
import com.ispan.eeit188_final.repository.CartRepository;

import jakarta.transaction.Transactional;
import tw.com.ispan.domain.DetailBean;
import tw.com.ispan.domain.ProductBean;
import tw.com.ispan.util.DatetimeConverter;

@Service
@Transactional
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	
	public Cart findById(CartId id) {
		if(id!=null) {
			Optional<Cart> optional = cartRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();				
			}
		}
		return null;
	}
	
	public Cart create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer id = obj.isNull("id") ? null : obj.getInt("id");
			String name = obj.isNull("name") ? null : obj.getString("name");
			Double price = obj.isNull("price") ? null : obj.getDouble("price");
			String make = obj.isNull("make") ? null : obj.getString("make");
			Integer expire = obj.isNull("expire") ? null : obj.getInt("expire");

			if(id!=null) {
				Optional<ProductBean> optional = productRepository.findById(id);
				if(optional.isEmpty()) {
					ProductBean insert = new ProductBean();
					insert.setId(id);
					insert.setName(name);
					insert.setPrice(price);
					insert.setMake(null);
					insert.setMake(DatetimeConverter.parse(make, "yyyy-MM-dd"));
					insert.setExpire(expire);

					return productRepository.save(insert);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Cart findById(CartId id) {
		if(id!=null) {
			Optional<Cart> optional = cartRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();				
			}
		}
		return null;
	}
	
	public Cart findById(CartId id) {
		if(id!=null) {
			Optional<Cart> optional = cartRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();				
			}
		}
		return null;
	}
	
	public Cart findById(CartId id) {
		if(id!=null) {
			Optional<Cart> optional = cartRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();				
			}
		}
		return null;
	}
}
