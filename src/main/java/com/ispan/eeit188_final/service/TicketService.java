package com.ispan.eeit188_final.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.Ticket;
import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.TicketRepository;
import com.ispan.eeit188_final.repository.specification.TicketSpecification;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	public Ticket findById(UUID id) {
		if (id != null) {
			Optional<Ticket> optional = ticketRepository.findById(id);
			if (optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}

	public List<Ticket> findByUser(User user) {
		if (user != null && user.getId() != null) {
			return ticketRepository.findByUserId(user.getId());
		}
		return null;
	}

	public List<Ticket> findByUserId(UUID userId) {
		if (userId != null) {
			return ticketRepository.findByUserId(userId);
		}
		return null;
	}

	public List<Ticket> findByHouse(House house) {
		if (house != null && house.getId() != null) {
			return ticketRepository.findByHouseId(house.getId());
		}
		return null;
	}

	public List<Ticket> findByHouseId(UUID houseId) {
		if (houseId != null) {
			return ticketRepository.findByHouseId(houseId);
		}
		return null;
	}

	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	public Page<Ticket> findAll(Integer pageNum, Integer pageSize, Boolean desc, String orderBy) {
		Integer defalutPageNum = 0;
		Integer defaultPageSize = 10;

		pageNum = pageNum == null ? defalutPageNum : pageNum;
		pageSize = pageSize == null|| pageSize == 0 ? defaultPageSize : pageSize;
		desc = desc == null ? false : desc;
		orderBy = orderBy == null || orderBy.length() == 0 ? "id" : orderBy;

		Pageable p = PageRequest.of(pageNum, pageSize, desc ? Direction.ASC : Direction.DESC, orderBy);

		return ticketRepository.findAll(p);
	}
	
	public Page<Ticket> findAll(String json) {
		Integer defalutPageNum = 0;
		Integer defaultPageSize = 10;
		
		JSONObject obj = new JSONObject(json);
		
		Integer pageNum = obj.isNull("pageNum") ? defalutPageNum : obj.getInt("pageNum");
		Integer pageSize = obj.isNull("pageSize") || obj.getInt("pageSize") == 0 ? defaultPageSize : obj.getInt("pageSize");
		Boolean desc = obj.isNull("desc") ? false : obj.getBoolean("desc");
		String orderBy = obj.isNull("orderBy") || obj.getString("orderBy").length() == 0 ? "id" : obj.getString("orderBy");

		Pageable p = PageRequest.of(pageNum, pageSize, desc ? Direction.ASC : Direction.DESC, orderBy);

		return ticketRepository.findAll(p);
	}

	public void deleteById(UUID id) {
		if (id != null) {
			ticketRepository.deleteById(id);
		}
	}

	public void deleteAll() {
		ticketRepository.deleteAll();
	}

	public Ticket create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			String qrCode = obj.isNull("qrCode") ? null : obj.getString("qrCode");
			String userIdString = obj.isNull("userId") ? null : obj.getString("userId");
			String houseIdString = obj.isNull("houseId") ? null : obj.getString("houseId");
			String startedAtString = obj.isNull("startedAt") ? null : obj.getString("startedAt");
			String endedAtString = obj.isNull("endedAt") ? null : obj.getString("endedAt");

			UUID userId = UUID.fromString(userIdString);
			UUID houseId = UUID.fromString(houseIdString);

			Timestamp startedAt = Timestamp.valueOf(startedAtString);
			Timestamp endedAt = Timestamp.valueOf(endedAtString);

			Ticket insert = new Ticket();
			insert.setQrCode(qrCode);
			insert.setUserId(userId);
			insert.setHouseId(houseId);
			insert.setStartedAt(startedAt);
			insert.setEndedAt(endedAt);

			return ticketRepository.save(insert);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Ticket create(Ticket ticket) {
		if (ticket.getCreatedAt() == null) {
			ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		}
		return ticketRepository.save(ticket);
	}

	public Ticket update(String json) {
		try {
			JSONObject obj = new JSONObject(json);

			UUID id = obj.isNull("id") ? null : UUID.fromString(obj.getString("id"));
			Ticket dbData = findById(id);
			if (dbData != null) {
				String qrCode = obj.isNull("qrCode") ? null : obj.getString("qrCode");
				String userIdString = obj.isNull("userId") ? null : obj.getString("userId");
				String houseIdString = obj.isNull("houseId") ? null : obj.getString("houseId");
				String startedAtString = obj.isNull("startedAt") ? null : obj.getString("startedAt");
				String endedAtString = obj.isNull("endedAt") ? null : obj.getString("endedAt");

				UUID userId = UUID.fromString(userIdString);
				UUID houseId = UUID.fromString(houseIdString);

				Timestamp startedAt;
				Timestamp endedAt;
				try {
					startedAt = Timestamp.valueOf(startedAtString);
					endedAt = Timestamp.valueOf(endedAtString);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					System.out.println("日期格式有誤");
					return null;
				}

				dbData.setQrCode(qrCode);
				dbData.setUserId(userId);
				dbData.setHouseId(houseId);
				dbData.setStartedAt(startedAt);
				dbData.setEndedAt(endedAt);

				return dbData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Ticket replace(Ticket ticket) {
		Optional<Ticket> dbTicket = ticketRepository.findById(ticket.getId());
		if (dbTicket.isPresent()) {
			return ticketRepository.save(ticket);
		}
		return null;
	}
	
	public Ticket modify(Ticket oldTicket, Ticket newTicket) {
		if(oldTicket != null && newTicket != null) {
			if(newTicket.getQrCode() != null) {
				oldTicket.setQrCode(newTicket.getQrCode());
			}
			if(newTicket.getHouseId() != null) {
				oldTicket.setHouseId(newTicket.getHouseId());
			}
			if(newTicket.getUserId() != null) {
				oldTicket.setUserId(newTicket.getUserId());
			}
			if(newTicket.getStartedAt() != null) {
				oldTicket.setStartedAt(newTicket.getStartedAt());
			}
			if(newTicket.getEndedAt() != null) {
				oldTicket.setEndedAt(newTicket.getEndedAt());
			}
			return ticketRepository.save(oldTicket);
		}
		return null;
	}

	public Page<Ticket> findByStarted(String json) {
		Integer defalutPageNum = 0;
		Integer defaultPageSize = 10;

		JSONObject obj = new JSONObject(json);
		Integer pageNum = obj.isNull("pageNum") ? defalutPageNum : obj.getInt("pageNum");
		Integer pageSize = obj.isNull("pageSize") || obj.getInt("pageSize") == 0 ? defaultPageSize : obj.getInt("pageSize");
		Boolean desc = obj.isNull("desc") ? false : obj.getBoolean("desc");
		String orderBy = obj.isNull("orderBy") || obj.getString("orderBy").length() == 0 ? "id" : obj.getString("orderBy");

		PageRequest pageRequest;
		if (orderBy != null) {
			pageRequest = PageRequest.of(pageNum, pageSize, desc ? Direction.ASC : Direction.DESC, orderBy);
		} else {
			pageRequest = PageRequest.of(pageNum, pageSize);
		}
		
		Specification<Ticket> spec = Specification.where(TicketSpecification.filterTickets(json));
		return ticketRepository.findAll(spec, pageRequest);
	}

}
