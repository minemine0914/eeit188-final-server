package com.ispan.eeit188_final.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.Cart;
import com.ispan.eeit188_final.model.Ticket;
import com.ispan.eeit188_final.model.composite.CartId;
import com.ispan.eeit188_final.repository.CartRepository;
import com.ispan.eeit188_final.repository.TicketRepository;

import jakarta.transaction.Transactional;
import tw.com.ispan.domain.DetailBean;
import tw.com.ispan.domain.ProductBean;
import tw.com.ispan.util.DatetimeConverter;

@Service
@Transactional
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	public Ticket findById(UUID id) {
		if(id!=null) {
			Optional<Ticket> optional = ticketRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();				
			}
		}
		return null;
	}
	
	public Ticket create(String json) {
		try {
			
			JSONObject obj = new JSONObject(json);
			UUID id = UUID.randomUUID();
			String qrCode = obj.isNull("qrCode") ? null : obj.getString("qrCode");
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
