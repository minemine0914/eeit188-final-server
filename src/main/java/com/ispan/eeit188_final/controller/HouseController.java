package com.ispan.eeit188_final.controller;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ispan.eeit188_final.dto.HouseDTO;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.service.HouseService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    /** 新增一筆 */
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody HouseDTO houseDTO) {
        House created = houseService.create(houseDTO);
        if (created != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(created.getId())
                    .toUri();
            return ResponseEntity.created(location).body(created); // Return 201 Created and redirect to created url
        }
        HouseDTO error = HouseDTO.builder().message("新增失敗").build();
        return ResponseEntity.badRequest().body(error); // Return 400 BadRequest
    }

    /** 查詢一筆 */
    @GetMapping("/{id}")
    public ResponseEntity<House> findById(@PathVariable UUID id) {
        House finded = houseService.findById(id);
        if (finded != null) {
            return ResponseEntity.ok(finded); // Return 200
        }
        return ResponseEntity.notFound().build(); // Return 404 NotFound
    }

    /** 修改一筆 */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody HouseDTO houseDTO) {
        House finded = houseService.findById(id);
        if (finded != null) {
            try {
                House updated = houseService.modify(id, houseDTO);
                if (updated != null) {
                    return ResponseEntity.ok(updated); // Return 200 and print updated entity
                }
            } catch (IllegalArgumentException e) {
                HouseDTO error = HouseDTO.builder().message(e.getMessage()).build();
                return ResponseEntity.badRequest().body(error); // Return 400 BadRequest
            }
            HouseDTO error = HouseDTO.builder().message("修改失敗，請檢查傳入值").build();
            return ResponseEntity.badRequest().body(error); // Return 400 BadRequest
        }
        return ResponseEntity.notFound().build(); // Return 404 NotFound
    }

    /** 刪除一筆 */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Boolean deleted = houseService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Return 204 NoContent
        }
        return ResponseEntity.notFound().build(); // Return 400 NotFound
    }

    /** 查詢所有 */
    @GetMapping("/all")
    public Page<House> all(@ModelAttribute HouseDTO houseDTO) {
        return houseService.findAll(houseDTO);
    }

    /** 查詢所有 */
    @GetMapping("/all-with-score")
    public Page<Map<String, Object>> allWithScore(@ModelAttribute HouseDTO houseDTO) {
        return houseService.findAllWithScores(houseDTO);
    }

    /**
     * 條件搜尋
     * JSON keys:
     * 基本資料: name, category, country, city, region
     * 基本設施: livingDiningRoom, bedroom, restroom, bathroom
     * 常態設施: kitchen, balcony
     * 刊登顯示: show
     * 擁有者Id: userId
     * 經緯區間: minLatitudeX, maxLatitudeX, minLongitudeY, maxLongitudeY
     * 價格區間: minPrice, maxPrice
     * 附加設施: postulateId, postulateIds, matchAllPostulates
     * 分頁限制: page, limit
     * 欄位排序: dir, order
     * 
     * 
     * 9/18新增 審核驗證:review
     */
    @PostMapping("/search")
    public Page<House> search(@RequestBody HouseDTO houseDTO) {
        return houseService.find(houseDTO);
    }

    @PostMapping("/search-with-score")
    public Page<Map<String, Object>> searchWithScore(@RequestBody HouseDTO houseDTO) {
        return houseService.findWithScores(houseDTO);
    }

}
