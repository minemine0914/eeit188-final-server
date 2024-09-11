package com.ispan.eeit188_final.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ispan.eeit188_final.dto.TicketDTO;
import com.ispan.eeit188_final.model.Ticket;
import com.ispan.eeit188_final.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable String id) {
    	if (id!=null&&id.length()!=0) {
    		UUID uuid = UUID.fromString(id);
    		Ticket ticket = ticketService.findById(uuid);
    		if(ticket!=null) {
    			return ResponseEntity.ok(ticket);
    		}
    	}
    	return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/findAll")
    public ResponseEntity<Page<Ticket>> findAll(@RequestBody TicketDTO ticketDTO){
    	Page<Ticket> tickets = ticketService.findAll(ticketDTO);
    	return ResponseEntity.ok(tickets);
    }
    
    @PostMapping("/")
    public ResponseEntity<Ticket> create(@RequestBody TicketDTO ticketDto) {
    	Ticket create = ticketService.create(ticketDto);
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(create.getId()).toUri();
    	return ResponseEntity.created(location).body(create);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable String id, @RequestBody TicketDTO ticketDto) {
    	if (id!=null&&id.length()!=0) {
    		UUID uuid = UUID.fromString(id);
    		Ticket dbTicket = ticketService.findById(uuid);
    		if(dbTicket!=null) {
    			Ticket modify = ticketService.modify(dbTicket, ticketDto);
    			if(modify != null) {
    				return ResponseEntity.ok(modify);
    			}
    			return ResponseEntity.badRequest().build();
    		}
    	}
    	return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
    	if (id!=null&&id.length()!=0) {
    		UUID uuid = UUID.fromString(id);
    		ticketService.deleteById(uuid);
    		return ResponseEntity.noContent().build();
    	}
    	return ResponseEntity.notFound().build();
    }
    
    
}