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

	// 紀錄某User是否對某Discuss按過: 愛心, 點擊, 分享, 評分

	// 一般查詢全部
	@GetMapping("/")
	public List<DiscussMongo> findAll() {
		return discussMongoService.findAll();
	}

	// 複雜條件查詢(目前只有排序用的4個變數)
	@PostMapping("/page")
	public Page<DiscussMongo> findAll(@RequestBody DiscussMongoDTO discussMongoDTO) {
		return discussMongoService.findAll(discussMongoDTO);
	}

	// 依資料ID查詢
	@GetMapping("/{id}")
	public DiscussMongo findById(@PathVariable UUID id) {
		return discussMongoService.findById(id);
	}

	// 使用userId和discussId查詢
	@PostMapping("/find")
	public DiscussMongo findByUserIdAndDiscussId(@RequestBody DiscussMongoDTO discussMongoDTO) {
		System.out.println(discussMongoDTO.getUserId());
		if (discussMongoDTO != null && discussMongoDTO.getUserId() != null && discussMongoDTO.getDiscussId() != null) {
			UUID userId = discussMongoDTO.getUserId();
			UUID discussId = discussMongoDTO.getDiscussId();

			return discussMongoService.findByUserIdAndDiscussId(userId, discussId);
		}
		return null;
	}
	
	// 使用userId和discussId查詢
	@GetMapping("/find/{userId}/{discussId}")
	public DiscussMongo findByUserIdAndDiscussId(@PathVariable UUID userId,@PathVariable UUID discussId) {
			return discussMongoService.findByUserIdAndDiscussId(userId, discussId);
	}

	// 新增
	@PostMapping("/")
	public DiscussMongo create(@RequestBody DiscussMongo discussMongo) {
		return discussMongoService.create(discussMongo);
	}

	// 修改
	@PutMapping("/")
	public DiscussMongo Update(@RequestBody DiscussMongo discussMongo) {
		return discussMongoService.update(discussMongo);
	}

	// 刪除
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable UUID id) {
		discussMongoService.deleteById(id);
	}

	// 計算愛心數
	@PostMapping("/count/like")
	public long countLikesForDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getDiscussId() != null) {
			UUID discussId = discussMongoDto.getDiscussId();
			return discussMongoService.countLikesForDiscuss(discussId);
		}
		return 0;
	}

	// 計算點擊數
	@PostMapping("/count/click")
	public long countClicksForDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getDiscussId() != null) {
			UUID discussId = discussMongoDto.getDiscussId();
			return discussMongoService.countClicksForDiscuss(discussId);
		}
		return 0;
	}

	// 計算分享次數
	@PostMapping("/count/share")
	public long countSharesForDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getDiscussId() != null) {
			UUID discussId = discussMongoDto.getDiscussId();
			return discussMongoService.countSharesForDiscuss(discussId);
		}
		return 0;
	}

	// 設為愛心
	@PostMapping("/like")
	public ResponseEntity<DiscussMongo> likeDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null) {
			DiscussMongo like = discussMongoService.likeDiscuss(discussMongoDto);
			return ResponseEntity.ok(like);
		}
		return ResponseEntity.noContent().build();
	}

	// 評分
	@PostMapping("/rate")
	public ResponseEntity<DiscussMongo> rateDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null) {
			DiscussMongo rate = discussMongoService.rateDiscuss(discussMongoDto);
			return ResponseEntity.ok(rate);
		}
		return ResponseEntity.noContent().build();
	}

	// 點擊+1
	@PostMapping("/click")
	public ResponseEntity<DiscussMongo> clickDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null) {
			DiscussMongo click = discussMongoService.clickDiscuss(discussMongoDto);
			return ResponseEntity.ok(click);
		}

		return ResponseEntity.noContent().build();
	}

	// 分享+1
	@PostMapping("/share")
	public ResponseEntity<DiscussMongo> shareDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null) {
			DiscussMongo share = discussMongoService.shareDiscuss(discussMongoDto);
			return ResponseEntity.ok(share);
		}

		return ResponseEntity.noContent().build();
	}

	// 重置愛心(設為沒按愛心)
	@PostMapping("/reset/like")
	public DiscussMongo resetLikeDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		return discussMongoService.resetLikeDiscuss(discussMongoDto);
	}

	// 重置評分(設為未評分)
	@PostMapping("/reset/rate")
	public DiscussMongo resetRateDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		return discussMongoService.resetRateDiscuss(discussMongoDto);
	}

	// 重置點擊(設為未點擊)
	@PostMapping("/reset/click")
	public DiscussMongo resetClickDiscuss(@RequestBody DiscussMongoDTO discussMongoDto) {
		return discussMongoService.resetClickDiscuss(discussMongoDto);
	}

	// 重置分享(設為未分享)
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
