package com.ispan.eeit188_final.controller;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.service.HouseService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    /** 新增一筆 */
    @PostMapping("/")
    public ResponseEntity<House> create(@RequestBody House house) {
        House created = houseService.create(house);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created); // Return 201 Created and redirect to created url
    }

    /** 查詢一筆 */
    @GetMapping("/{id}")
    public ResponseEntity<House> findById(@PathVariable String id) {
        House finded = houseService.findById(UUID.fromString(id));
        if (finded != null) {
            return ResponseEntity.ok(finded); // Return 200
        }
        return ResponseEntity.notFound().build(); // Return 404 NotFound
    }

    /** 修改一筆 */
    @PutMapping("/{id}")
    public ResponseEntity<House> update(@PathVariable String id, @RequestBody House house) {
        House finded = houseService.findById(UUID.fromString(id));
        if (finded != null) {
            House updated = houseService.modify(UUID.fromString(id), house);
            if (updated != null) {
                return ResponseEntity.ok(updated); // Return 200 and print updated entity
            }
            return ResponseEntity.badRequest().build(); // Return 400 BadRequest
        }
        return ResponseEntity.notFound().build(); // Return 404 NotFound
    }

    /** 刪除一筆 */
    @DeleteMapping("/{id}")
    public ResponseEntity<House> delete(@PathVariable String id) {
        boolean deleted = houseService.delete(UUID.fromString(id));
        if (deleted) {
            return ResponseEntity.noContent().build(); // Return 204 NoContent
        }
        return ResponseEntity.notFound().build(); // Return 400 NotFound
    }

    /** 查詢所有 */
    @GetMapping("/all")
    public Page<House> all(@RequestParam Map<String, String> allParams) {
        JSONObject jsonParams = new JSONObject(allParams);
        return houseService.findAll(jsonParams.toString());
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
     * 分頁限制: page, limit
     * 欄位排序: dir, order
     */
    @PostMapping("/search")
    public Page<House> search(@RequestBody String jsonString) {
        return houseService.find(jsonString);
    }
}
