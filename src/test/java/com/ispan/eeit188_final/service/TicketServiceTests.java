package com.ispan.eeit188_final.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.ispan.eeit188_final.model.Ticket;

@SpringBootTest
public class TicketServiceTests {

	@Autowired
	private TicketService ticketService;

//	@Test
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

//	@Test
	public void testFindAll() {
		List<Ticket> list = ticketService.findAll();
		for (Ticket ticket : list) {
			System.out.println(ticket);
		}
	}

	@Test
	public void testFindAllPage() {
		Page<Ticket> page = ticketService.findAll(1, 2, true, "id");
//		Page<Ticket> page = ticketService.findAll(1, 2, false, "id");
//		Page<Ticket> page = ticketService.findAll(2, 2, false, "id");
		for (Ticket ticket : page) {
			System.out.println(ticket.getId());
		}
	}

}
