package com.ispan.eeit188_final.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.dto.TicketDTO;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.Ticket;
import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.HouseRepository;
import com.ispan.eeit188_final.repository.TicketRepository;
import com.ispan.eeit188_final.repository.UserRepository;
import com.ispan.eeit188_final.repository.specification.TicketSpecification;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TicketService {

	private static final Integer PAGEABLE_DEFAULT_PAGE = 0;
	private static final Integer PAGEABLE_DEFAULT_LIMIT = 10;
	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private HouseRepository houseRepository;

	@Autowired
	private UserRepository userRepository;

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
		pageNum = pageNum == null ? PAGEABLE_DEFAULT_PAGE : pageNum;
		pageSize = pageSize == null || pageSize == 0 ? PAGEABLE_DEFAULT_LIMIT : pageSize;
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
		Integer pageSize = obj.isNull("pageSize") || obj.getInt("pageSize") == 0 ? defaultPageSize
				: obj.getInt("pageSize");
		Boolean desc = obj.isNull("desc") ? false : obj.getBoolean("desc");
		String orderBy = obj.isNull("orderBy") || obj.getString("orderBy").length() == 0 ? "id"
				: obj.getString("orderBy");

		Pageable p = PageRequest.of(pageNum, pageSize, desc ? Direction.ASC : Direction.DESC, orderBy);

		return ticketRepository.findAll(p);
	}

	public Page<Ticket> findAll(TicketDTO ticketDTO) {
		// 頁數 限制 排序
		Integer page = Optional.ofNullable(ticketDTO.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
		Integer limit = Optional.ofNullable(ticketDTO.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
		Boolean dir = Optional.ofNullable(ticketDTO.getDir()).orElse(false);
		String order = Optional.ofNullable(ticketDTO.getOrder()).orElse(null);
		// 是否排序
		Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
		return ticketRepository.findAll(PageRequest.of(page, limit, sort));
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
			String userIdString = obj.isNull("userId") ? null : obj.getString("userId");
			String houseIdString = obj.isNull("houseId") ? null : obj.getString("houseId");
			if (userIdString != null && userIdString.length() != 0 && houseIdString != null
					&& houseIdString.length() != 0) {
				Optional<User> findUser = userRepository.findById(UUID.fromString(userIdString));
				Optional<House> findHouse = houseRepository.findById(UUID.fromString(houseIdString));
				if (findUser.isPresent() && findHouse.isPresent()) {
					String qrCode = obj.isNull("qrCode") ? null : obj.getString("qrCode");
					String startedAtString = obj.isNull("startedAt") ? null : obj.getString("startedAt");
					String endedAtString = obj.isNull("endedAt") ? null : obj.getString("endedAt");

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Timestamp startedAt = Timestamp.valueOf(sdf.format(startedAtString));
					Timestamp endedAt = Timestamp.valueOf((endedAtString));

					Ticket insert = Ticket.builder().qrCode(qrCode).user(findUser.isPresent() ? findUser.get() : null)
							.house(findHouse.isPresent() ? findHouse.get() : null).startedAt(startedAt).endedAt(endedAt)
							.build();

					return ticketRepository.save(insert);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("日期格式有誤");
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

	public Ticket create(TicketDTO ticketDto) {
		if (ticketDto.getHouseId() != null && ticketDto.getUserId() != null) {
			Optional<House> findHouse = houseRepository.findById(ticketDto.getHouseId());
			Optional<User> findUser = userRepository.findById(ticketDto.getUserId());
			if (findHouse.isPresent() && findUser.isPresent()) {
				Ticket ticket = Ticket.builder()
						.qrCode(ticketDto.getQrCode())
						.user(findUser.get())
						.house(findHouse.get())
						.startedAt(ticketDto.getStartedAt())
						.endedAt(ticketDto.getEndedAt())
						.createdAt(ticketDto.getCreatedAt() == null ? new Timestamp(System.currentTimeMillis())
								: ticketDto.getCreatedAt())
						.build();
				return ticketRepository.save(ticket);
			}

		}
		return null;
	}

	public Ticket update(String json) {
		try {
			JSONObject obj = new JSONObject(json);

			UUID id = obj.isNull("id") ? null : UUID.fromString(obj.getString("id"));
			Ticket dbData = findById(id);
			if (dbData != null) {
				String userIdString = obj.isNull("userId") ? null : obj.getString("userId");
				String houseIdString = obj.isNull("houseId") ? null : obj.getString("houseId");
				if (userIdString != null && userIdString.length() != 0 && houseIdString != null
						&& houseIdString.length() != 0) {
					Optional<User> findUser = userRepository.findById(UUID.fromString(userIdString));
					Optional<House> findHouse = houseRepository.findById(UUID.fromString(houseIdString));
					if (findUser.isPresent() && findHouse.isPresent()) {
						String qrCode = obj.isNull("qrCode") ? null : obj.getString("qrCode");
						String startedAtString = obj.isNull("startedAt") ? null : obj.getString("startedAt");
						String endedAtString = obj.isNull("endedAt") ? null : obj.getString("endedAt");
						Timestamp startedAt;
						Timestamp endedAt;

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						startedAt = Timestamp.valueOf(sdf.format(startedAtString));
						endedAt = Timestamp.valueOf((endedAtString));

						dbData.setQrCode(qrCode);
						dbData.setUser(findUser.isPresent() ? findUser.get() : null);
						dbData.setHouse(findHouse.isPresent() ? findHouse.get() : null);
						dbData.setStartedAt(startedAt);
						dbData.setEndedAt(endedAt);

						return dbData;
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("日期格式有誤");
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

	public Ticket modify(Ticket oldTicket, TicketDTO ticketDto) {
		if (oldTicket != null && ticketDto != null) {
			if (ticketDto.getQrCode() != null) {
				oldTicket.setQrCode(ticketDto.getQrCode());
			}
			if (ticketDto.getHouseId() != null) {
				Optional<House> findHouse = houseRepository.findById(ticketDto.getHouseId());
				if (findHouse.isPresent()) {
					oldTicket.setHouse(findHouse.get());
				}
			}
			if (ticketDto.getUserId() != null) {
				Optional<User> findUser = userRepository.findById(ticketDto.getUserId());
				if (findUser.isPresent()) {
					oldTicket.setUser(findUser.get());
				}
			}
			if (ticketDto.getStartedAt() != null) {
				oldTicket.setStartedAt(ticketDto.getStartedAt());
			}
			if (ticketDto.getEndedAt() != null) {
				oldTicket.setEndedAt(ticketDto.getEndedAt());
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
		Integer pageSize = obj.isNull("pageSize") || obj.getInt("pageSize") == 0 ? defaultPageSize
				: obj.getInt("pageSize");
		Boolean desc = obj.isNull("desc") ? false : obj.getBoolean("desc");
		String orderBy = obj.isNull("orderBy") || obj.getString("orderBy").length() == 0 ? "id"
				: obj.getString("orderBy");

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
