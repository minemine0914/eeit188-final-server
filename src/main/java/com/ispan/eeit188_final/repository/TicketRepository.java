package com.ispan.eeit188_final.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, UUID>, JpaSpecificationExecutor<Ticket> {

	public List<Ticket> findByUserId(UUID userId);

	public List<Ticket> findByHouseId(UUID houseId);

	@Query("SELECT COUNT(t) FROM Ticket t WHERE (t.startedAt < CURRENT_TIMESTAMP OR t.endedAt > CURRENT_TIMESTAMP) AND t.house = :house AND t.used = false")
	Long countNotUsedTicketsByHouse(House house);

	@Query("SELECT COUNT(t) FROM Ticket t WHERE t.house = :house " +
       "AND (t.startedAt <= :end AND t.endedAt >= :start)")
Long countOverlappingTickets(
        @Param("house") House house,
        @Param("start") Timestamp start,
        @Param("end") Timestamp end);
}
