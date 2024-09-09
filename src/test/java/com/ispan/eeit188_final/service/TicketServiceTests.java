package com.ispan.eeit188_final.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.ispan.eeit188_final.model.Ticket;

@SpringBootTest
public class TicketServiceTests {

	@Autowired
	private TicketService ticketService;

	 @Test
	public void testCreate() {
		Ticket ticket = new Ticket();
		ticket.setQrCode("TEST_QRCODE");
		// ======to be modified after @ManyToOne========
		ticket.setUserId(UUID.randomUUID());
		ticket.setHouseId(UUID.randomUUID());
		// =============================================
		ticket.setStartedAt(new Timestamp(86400000));
		ticket.setEndedAt(new Timestamp(3600000));
		ticket.setCreatedAt(new Timestamp(86400000));

		System.out.println(ticketService.create(ticket));
	}

	 @Test
	public void testFindAll() {
		List<Ticket> list = ticketService.findAll();
		for (Ticket ticket : list) {
			System.out.println(ticket);
		}
	}

	@Test
	public void testFindById() {
		String id = "107ad012-cec6-4394-b7a0-d59bf38e7468";
		UUID uuid = UUID.fromString(id);
		Ticket ticket = ticketService.findById(uuid);
		System.out.println(ticket);
	}

	 @Test
	public void testFindAllPage() {

		Page<Ticket> page = null;
		page = ticketService.findAll(null, null, null, null);
		// page = ticketService.findAll(null, null, true, null);

		// page = ticketService.findAll(1, 2, true, "id");
		// page = ticketService.findAll(1, 2, false, "id");
		// page = ticketService.findAll(2, 2, false, "id");

		for (Ticket ticket : page) {
			System.out.println(ticket.getId());
		}
	}

	 @Test
	public void testFindAllPageJson() {
		List<Integer> pageNums = Arrays.asList(0, 1, null);
		List<Integer> pageSizes = Arrays.asList(0, 1, null);
		List<Boolean> descs = Arrays.asList(true, false, null);
		List<String> orderBys = Arrays.asList("", "id", null);
		Page<Ticket> page = null;
		for (Integer pageNum : pageNums) {
			for (Integer pageSize : pageSizes) {
				for (Boolean desc : descs) {
					for (String orderBy : orderBys) {
						JSONObject obj = new JSONObject()
								.put("pageNum", pageNum)
								.put("pageSize", pageSize)
								.put("desc", desc)
								.put("orderBy", orderBy);

						String json = obj.toString();
						page = ticketService.findAll(json);

						System.out.printf("pageNum=%d, pageSize=%d, desc=%b, orderBy=%s\r\n", pageNum, pageSize, desc,
								orderBy);
						for (Ticket ticket : page) {
							System.out.println(ticket.getId());
						}
					}
				}
			}
		}
	}

	@Test
	public void testFindByStarted() {
		JSONObject obj = new JSONObject()
				.put("pageNum", 0)
				.put("pageSize", 0)
				.put("desc", false)
				.put("orderBy", "id")
		// .put("userId", "")
		// .put("houseId","")
		// .put("minStart",new SimpleDateFormat("yyyy-MM-dd").format(new Date(0)))
		// .put("maxStart",new SimpleDateFormat("yyyy-MM-dd").format(new
		// Date(System.currentTimeMillis())))
		;

		String json = obj.toString();
		Page<Ticket> page = null;
		page = ticketService.findByStarted(json);
		for (Ticket ticket : page) {
			System.out.println(ticket.getId());
		}
	}

}
