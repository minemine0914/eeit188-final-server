package com.ispan.eeit188_final.controller;

import java.util.List;
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

import com.ispan.eeit188_final.dto.DiscussMongoDTO;
import com.ispan.eeit188_final.model.DiscussMongo;
import com.ispan.eeit188_final.service.DiscussMongoService;

@RestController
@CrossOrigin
@RequestMapping("/discuss/mongo")
public class DiscussMongoController {
	
	@Autowired
	private DiscussMongoService discussMongoService;

	@GetMapping("/")
	public List<DiscussMongo> findAll() {
		return discussMongoService.findAll();
	}

	@PostMapping("/page")
	public Page<DiscussMongo> findAll(DiscussMongoDTO discussMongoDTO) {
		return discussMongoService.findAll(discussMongoDTO);
	}
	
	@GetMapping("/{id}")
	public DiscussMongo findById(@PathVariable UUID id) {
		return discussMongoService.findById(id);
	}

	@PostMapping("/")
	public DiscussMongo create(@RequestBody DiscussMongo discussMongo) {
		return discussMongoService.create(discussMongo);
	}
	
	@PutMapping("/")
	public DiscussMongo Update(@RequestBody DiscussMongo discussMongo) {
		return discussMongoService.update(discussMongo);
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable UUID id) {
		discussMongoService.deleteById(id);
	}

	@PostMapping("/like")
	public ResponseEntity<DiscussMongo> likeDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null) {
    		DiscussMongo like = discussMongoService.likeDiscuss(discussMongoDto);
    			return ResponseEntity.ok(like);
    		}
    	
    	return ResponseEntity.noContent().build();
	}

	@PostMapping("/rate")
	public ResponseEntity<DiscussMongo> rateDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null) {
    		DiscussMongo rate = discussMongoService.rateDiscuss(discussMongoDto);
    			return ResponseEntity.ok(rate);
    		}
    	
    	return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/click")
	public ResponseEntity<DiscussMongo> clickDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null) {
    		DiscussMongo click = discussMongoService.clickDiscuss(discussMongoDto);
    			return ResponseEntity.ok(click);
    		}
    	
    	return ResponseEntity.noContent().build();
	}

	@PostMapping("/share")
	public ResponseEntity<DiscussMongo> shareDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null) {
    		DiscussMongo share = discussMongoService.shareDiscuss(discussMongoDto);
    			return ResponseEntity.ok(share);
    		}
    	
    	return ResponseEntity.noContent().build();
	}

	@PostMapping("/reset/like")
	public DiscussMongo resetLikeDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		return discussMongoService.resetLikeDiscuss(discussMongoDto);
	}

	@PostMapping("/reset/rate")
	public DiscussMongo resetRateDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		return discussMongoService.resetRateDiscuss(discussMongoDto);
	}
	
	@PostMapping("/reset/click")
	public DiscussMongo resetClickDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		return discussMongoService.resetClickDiscuss(discussMongoDto);
	}

	@PostMapping("/reset/share")
	public DiscussMongo resetShareDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		return discussMongoService.resetShareDiscuss(discussMongoDto);
	}
	
//	@GetMapping("/{id}/{rating}")
//	public DiscussMongo getRating(@PathVariable(name = "id") UUID id, @PathVariable(name = "rating") Integer rating) {
//		DiscussMongo cc = discussMongoRepository.findById(id).orElse(new DiscussMongo());
//		switch (rating) {
//		case 1:
//			cc.getScore()[0]++;
//			cc.setClick(cc.getClick() + 1);
//			break;
//		case 2:
//			cc.getScore()[1]++;
//			cc.setClick(cc.getClick() + 1);
//			break;
//		case 3:
//			cc.getScore()[2]++;
//			cc.setClick(cc.getClick() + 1);
//
//			break;
//		case 4:
//			cc.getScore()[3]++;
//			cc.setClick(cc.getClick() + 1);
//			break;
//		case 5:
//			cc.getScore()[4]++;
//			cc.setClick(cc.getClick() + 1);
//			break;
//		}
//		discussMongoRepository.save(cc);
//
//		return discussMongoRepository.findById(id).orElse(new DiscussMongo());
//	}
}
