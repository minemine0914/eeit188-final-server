package com.ispan.eeit188_final.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.eeit188_final.model.Ticket;


public interface TicketRepository extends JpaRepository<Ticket, UUID>, JpaSpecificationExecutor<Ticket> {

	public List<Ticket> findByUserId(UUID userId);
	public List<Ticket> findByHouseId(UUID houseId);
//	public Long countByHouseId
}
