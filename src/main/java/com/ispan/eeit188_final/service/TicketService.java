package com.ispan.eeit188_final.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.Ticket;
import com.ispan.eeit188_final.repository.TicketRepository;

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

	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	public Page<Ticket> findAll(Integer pageNum, Integer pageSize, Boolean desc, String orderBy) {
		// pageNum starts from 1
		Pageable p = PageRequest.of(pageNum - 1, pageSize, desc ? Sort.Direction.ASC : Sort.Direction.DESC, orderBy);
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

			UUID id = obj.isNull("id") ? null : UUID.fromString(obj.getString(json));
			Ticket dbData = findById(id);
			if (dbData != null) {
				String qrCode = obj.isNull("qrCode") ? null : obj.getString("qrCode");
				String userIdString = obj.isNull("userId") ? null : obj.getString("userId");
				String houseIdString = obj.isNull("houseId") ? null : obj.getString("houseId");
				String startedAtString = obj.isNull("startedAt") ? null : obj.getString("startedAt");
				String endedAtString = obj.isNull("endedAt") ? null : obj.getString("endedAt");

				UUID userId = UUID.fromString(userIdString);
				UUID houseId = UUID.fromString(houseIdString);

				Timestamp startedAt = Timestamp.valueOf(startedAtString);
				Timestamp endedAt = Timestamp.valueOf(endedAtString);

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

	public Ticket update(Ticket ticket) {
		Optional<Ticket> dbTicket = ticketRepository.findById(ticket.getId());
		if (dbTicket.isPresent()) {
			return ticketRepository.save(ticket);
		}
		return null;
	}

}
