package com.ispan.eeit188_final.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.dto.DiscussMongoDTO;
import com.ispan.eeit188_final.model.DiscussMongo;
import com.ispan.eeit188_final.repository.DiscussMongoRepository;
import com.ispan.eeit188_final.repository.DiscussRepository;
import com.ispan.eeit188_final.repository.UserRepository;

@Service
public class DiscussMongoService {

	// 紀錄某User是否對某Discuss按過: 愛心, 點擊, 分享, 評分
	
	private static final Integer PAGEABLE_DEFAULT_PAGE = 0;
	private static final Integer PAGEABLE_DEFAULT_LIMIT = 10;

	@Autowired
	private DiscussMongoRepository discussMongoRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DiscussRepository discussRepository;

	public List<DiscussMongo> findAll() {
		return discussMongoRepository.findAll();
	}

	public Page<DiscussMongo> findAll(DiscussMongoDTO discussMongoDTO) {
		// 頁數 限制 排序
		Integer page = Optional.ofNullable(discussMongoDTO.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
		Integer limit = Optional.ofNullable(discussMongoDTO.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
		Boolean dir = Optional.ofNullable(discussMongoDTO.getDir()).orElse(false);
		String order = Optional.ofNullable(discussMongoDTO.getOrder()).orElse(null);
		// 是否排序
		Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
		return discussMongoRepository.findAll(PageRequest.of(page, limit, sort));
	}

	public DiscussMongo findById(UUID id) {
		return discussMongoRepository.findById(id).orElse(null);
	}

	public List<DiscussMongo> findByUserId(UUID id) {
		List<DiscussMongo> discusss = discussMongoRepository.findByUserId(id);
		if (discusss != null && discusss.size() != 0) {
			return discusss;
		}
		return null;
	}

	public List<DiscussMongo> findByDiscussId(UUID id) {
		List<DiscussMongo> discusss = discussMongoRepository.findByDiscussId(id);
		if (discusss != null && discusss.size() != 0) {
			return discusss;
		}
		return null;
	}

	public DiscussMongo findByUserIdAndDiscussId(UUID userId, UUID discussId) {
		DiscussMongo discuss = discussMongoRepository.findByUserIdAndDiscussId(userId, discussId);
		if (discuss != null) {
			return discuss;
		}
		return null;
	}

	public DiscussMongo create(DiscussMongo discussMongo) {
		if (discussMongo != null && discussMongo.getUserId() != null && discussMongo.getDiscussId() != null
				&& userRepository.findById(discussMongo.getUserId()).isPresent()
				&& discussRepository.findById(discussMongo.getDiscussId()).isPresent()) {

			DiscussMongo dbDiscussMongo = discussMongoRepository.findByUserIdAndDiscussId(discussMongo.getUserId(),
					discussMongo.getDiscussId());
			if (dbDiscussMongo == null) {
				return discussMongoRepository.save(discussMongo);
			}
		}
		return null;
	}

	public DiscussMongo update(DiscussMongo discussMongo) {
		return discussMongoRepository.save(discussMongo);
	}

	public void deleteById(UUID id) {
		discussMongoRepository.deleteById(id);
	}

	//回傳該Discuss的愛心總數
	public long countLikesForDiscuss(UUID discussId) {
		return discussMongoRepository.countByDiscussIdAndLikedTrue(discussId);
	}

	// 回傳該Discuss的被點擊總數
	public long countClicksForDiscuss(UUID discussId) {
		return discussMongoRepository.countByDiscussIdAndLikedTrue(discussId);
	}

	// 回傳該Discuss的被分享總數
	public long countSharesForDiscuss(UUID discussId) {
		return discussMongoRepository.countByDiscussIdAndLikedTrue(discussId);
	}

	// 設為愛心 & 取消愛心
	public DiscussMongo likeDiscuss(DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null
				&& userRepository.findById(discussMongoDto.getUserId()).isPresent()
				&& discussRepository.findById(discussMongoDto.getDiscussId()).isPresent()) {
			DiscussMongo dbDiscuss = discussMongoRepository.findByUserIdAndDiscussId(discussMongoDto.getUserId(),
					discussMongoDto.getDiscussId());

			if (dbDiscuss == null) {
				dbDiscuss = new DiscussMongo();
				dbDiscuss.setUserId(discussMongoDto.getUserId());
				dbDiscuss.setDiscussId(discussMongoDto.getDiscussId());
			}

			// 先判定更新前狀態，再更新(順序不可顛倒)
			dbDiscuss.setLikeDate(dbDiscuss.getLiked() == false ? new Date() : null);
			dbDiscuss.setLiked(!dbDiscuss.getLiked());

			return discussMongoRepository.save(dbDiscuss);
		}
		return null;
	}

	// 評分
	public DiscussMongo rateDiscuss(DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null
				&& userRepository.findById(discussMongoDto.getUserId()).isPresent()
				&& discussRepository.findById(discussMongoDto.getDiscussId()).isPresent()) {
			DiscussMongo dbDiscuss = discussMongoRepository.findByUserIdAndDiscussId(discussMongoDto.getUserId(),
					discussMongoDto.getDiscussId());

			if (dbDiscuss == null) {
				dbDiscuss = new DiscussMongo();
				dbDiscuss.setUserId(discussMongoDto.getUserId());
				dbDiscuss.setDiscussId(discussMongoDto.getDiscussId());
			}

			if (discussMongoDto.getScore() >= 1 && discussMongoDto.getScore() <= 5) {
				dbDiscuss.setScoreDate(
						dbDiscuss.getScore() == 0 || dbDiscuss.getScore() != discussMongoDto.getScore() ? new Date()
								: dbDiscuss.getScoreDate());
				dbDiscuss.setScore(discussMongoDto.getScore());
			}

			return discussMongoRepository.save(dbDiscuss);
		}
		return null;
	}

	// 判斷User是否對Discuss點過查詢(???時間內(永遠)不重複計算)
	public DiscussMongo clickDiscuss(DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null
				&& userRepository.findById(discussMongoDto.getUserId()).isPresent()
				&& discussRepository.findById(discussMongoDto.getDiscussId()).isPresent()) {
			DiscussMongo dbDiscuss = discussMongoRepository.findByUserIdAndDiscussId(discussMongoDto.getUserId(),
					discussMongoDto.getDiscussId());

			if (dbDiscuss == null) {
				dbDiscuss = new DiscussMongo();
				dbDiscuss.setUserId(discussMongoDto.getUserId());
				dbDiscuss.setDiscussId(discussMongoDto.getDiscussId());
			}

			if (!dbDiscuss.getClicked()) {
				dbDiscuss.setClicked(true);
				dbDiscuss.setClickDate(new Date());
			}

			return discussMongoRepository.save(dbDiscuss);
		}
		return null;
	}

	// 判斷User是否對Discuss點過分享(永遠不重複計算)
	public DiscussMongo shareDiscuss(DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null
				&& userRepository.findById(discussMongoDto.getUserId()).isPresent()
				&& discussRepository.findById(discussMongoDto.getDiscussId()).isPresent()) {
			DiscussMongo dbDiscuss = discussMongoRepository.findByUserIdAndDiscussId(discussMongoDto.getUserId(),
					discussMongoDto.getDiscussId());

			if (dbDiscuss == null) {
				dbDiscuss = new DiscussMongo();
				dbDiscuss.setUserId(discussMongoDto.getUserId());
				dbDiscuss.setDiscussId(discussMongoDto.getDiscussId());
			}

			if (!dbDiscuss.getShared()) {
				dbDiscuss.setShared(true);
				dbDiscuss.setShareDate(new Date());
			}

			return discussMongoRepository.save(dbDiscuss);
		}
		return null;
	}

	// 重置like(=false)
	public DiscussMongo resetLikeDiscuss(DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null
				&& userRepository.findById(discussMongoDto.getUserId()).isPresent()
				&& discussRepository.findById(discussMongoDto.getDiscussId()).isPresent()) {
			DiscussMongo dbDiscuss = discussMongoRepository.findByUserIdAndDiscussId(discussMongoDto.getUserId(),
					discussMongoDto.getDiscussId());
			if (dbDiscuss != null) {
				dbDiscuss.setLiked(false);
				dbDiscuss.setLikeDate(null);
				return discussMongoRepository.save(dbDiscuss);
			}
		}
		return null;
	}

	// 重置評分(=0)
	public DiscussMongo resetRateDiscuss(DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null
				&& userRepository.findById(discussMongoDto.getUserId()).isPresent()
				&& discussRepository.findById(discussMongoDto.getDiscussId()).isPresent()) {
			DiscussMongo dbDiscuss = discussMongoRepository.findByUserIdAndDiscussId(discussMongoDto.getUserId(),
					discussMongoDto.getDiscussId());
			if (dbDiscuss != null) {
				dbDiscuss.setScoreDate(null);
				dbDiscuss.setScore(0);
				return discussMongoRepository.save(dbDiscuss);
			}
		}
		return null;
	}

	// 重置click(=false)
	public DiscussMongo resetClickDiscuss(DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null
				&& userRepository.findById(discussMongoDto.getUserId()).isPresent()
				&& discussRepository.findById(discussMongoDto.getDiscussId()).isPresent()) {
			DiscussMongo dbDiscuss = discussMongoRepository.findByUserIdAndDiscussId(discussMongoDto.getUserId(),
					discussMongoDto.getDiscussId());
			if (dbDiscuss != null) {
				dbDiscuss.setClicked(false);
				dbDiscuss.setClickDate(null);
				return discussMongoRepository.save(dbDiscuss);
			}
		}
		return null;
	}

	// 重置share
	public DiscussMongo resetShareDiscuss(DiscussMongoDTO discussMongoDto) {
		if (discussMongoDto != null && discussMongoDto.getUserId() != null && discussMongoDto.getDiscussId() != null
				&& userRepository.findById(discussMongoDto.getUserId()).isPresent()
				&& discussRepository.findById(discussMongoDto.getDiscussId()).isPresent()) {
			DiscussMongo dbDiscuss = discussMongoRepository.findByUserIdAndDiscussId(discussMongoDto.getUserId(),
					discussMongoDto.getDiscussId());
			if (dbDiscuss != null) {
				dbDiscuss.setShared(false);
				dbDiscuss.setShareDate(null);
				return discussMongoRepository.save(dbDiscuss);
			}
		}
		return null;
	}

	// 評分功能(1~5分別計算數量)
	// @GetMapping("/{id}/{rating}")
	// public DiscussMongo getRating(@PathVariable(name = "id") UUID id,
	// @PathVariable(name = "rating") Integer rating) {
	// DiscussMongo cc = discussMongoRepository.findById(id).orElse(new DiscussMongo());
	// switch (rating) {
	// case 1:
	// cc.getScore()[0]++;
	// cc.setClick(cc.getClick() + 1);
	// break;
	// case 2:
	// cc.getScore()[1]++;
	// cc.setClick(cc.getClick() + 1);
	// break;
	// case 3:
	// cc.getScore()[2]++;
	// cc.setClick(cc.getClick() + 1);
	//
	// break;
	// case 4:
	// cc.getScore()[3]++;
	// cc.setClick(cc.getClick() + 1);
	// break;
	// case 5:
	// cc.getScore()[4]++;
	// cc.setClick(cc.getClick() + 1);
	// break;
	// }
	// discussMongoRepository.save(cc);
	//
	// return discussMongoRepository.findById(id).orElse(new DiscussMongo());
	// }
}
