package com.ispan.eeit188_final.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ispan.eeit188_final.dto.PriceRangeDTO;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.PriceRange;
import com.ispan.eeit188_final.service.HouseService;
import com.ispan.eeit188_final.service.PriceRangeService;

@CrossOrigin
@RestController
@RequestMapping("/price_range")
public class PriceRangeController {
    @Autowired
    private PriceRangeService priceRangeService;

    @Autowired
    HouseService houseService;

    /** 新增一筆 */
    @PostMapping("/")
    public ResponseEntity<PriceRange> create(@RequestBody PriceRangeDTO priceRangeDTO) {
        House findHouse = houseService.findById(priceRangeDTO.getHouseId());
        if (findHouse != null) {
            PriceRange priceRange = PriceRange.builder()
                    .house(findHouse)
                    .newPrice(priceRangeDTO.getNewPrice())
                    .startedAt(priceRangeDTO.getStartedAt())
                    .endedAt(priceRangeDTO.getEndedAt())
                    .build();
            PriceRange created = priceRangeService.create(priceRange);
            if (created != null) {
                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(created.getId())
                        .toUri();
                return ResponseEntity.created(location).body(created); // Return 201 Created and redirect to created url
            }
            return ResponseEntity.badRequest().build(); // Return 400 BadRequest
        }
        return ResponseEntity.notFound().build();// Return 404 NotFound (House id not found)
    }

    /** 查詢一筆 */
    @GetMapping("/{id}")
    public ResponseEntity<PriceRange> findById(@PathVariable String id) {
        PriceRange finded = priceRangeService.findById(UUID.fromString(id));
        if (finded != null) {
            return ResponseEntity.ok(finded); // Return 200
        }
        return ResponseEntity.notFound().build(); // Return 404 NotFound
    }

    /** 修改一筆 */
    @PutMapping("/{id}")
    public ResponseEntity<PriceRange> update(@PathVariable String id, @RequestBody PriceRange priceRange) {
        PriceRange finded = priceRangeService.findById(UUID.fromString(id));
        if (finded != null) {
            PriceRange updated = priceRangeService.modify(UUID.fromString(id), priceRange);
            if (updated != null) {
                return ResponseEntity.ok(updated); // Return 200 and print updated entity
            }
            return ResponseEntity.badRequest().build(); // Return 400 BadRequest
        }
        return ResponseEntity.notFound().build(); // Return 404 NotFound
    }

    /** 刪除一筆 */
    @DeleteMapping("/{id}")
    public ResponseEntity<PriceRange> delete(@PathVariable String id) {
        boolean deleted = priceRangeService.delete(UUID.fromString(id));
        if (deleted) {
            return ResponseEntity.noContent().build(); // Return 204 NoContent
        }
        return ResponseEntity.notFound().build(); // Return 400 NotFound
    }

    /** 查詢所有 */
    @GetMapping("/all")
    public Page<PriceRange> all(@ModelAttribute PriceRangeDTO priceRangeDTO) {
        return priceRangeService.findAll(priceRangeDTO);
    }

    /**
     * 條件搜尋
     * JSON keys:
     * 房源 Id: houseId
     * 價錢區間: minPrice, maxPrice
     * 時間區間: startedAt, endedAt
     * 分頁限制: page, limit
     * 欄位排序: dir, order
     */
    @PostMapping("/search")
    public Page<PriceRange> search(@RequestBody PriceRangeDTO priceRangeDTO) {
        return priceRangeService.find(priceRangeDTO);
    }

}
