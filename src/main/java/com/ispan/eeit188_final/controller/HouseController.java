package com.ispan.eeit188_final.controller;

import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.service.HouseService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseService houseService;

    @GetMapping("/{id}")
    public ResponseEntity<House> findById(@PathVariable String id) {
        House house = houseService.findById(UUID.fromString(id));
        if (house != null) {
            return ResponseEntity.ok(house);
        }
        return ResponseEntity.notFound().build(); // Return 404 NotFound
    }

    @GetMapping("/all")
    public Page<House> all(@RequestParam Map<String, String> allParams) {
        // Example: http://localhost:8080/house/all?page=0&limit=10&dir=true&order=createdAt
        JSONObject jsonParams = new JSONObject(allParams);
        return houseService.findAll(jsonParams.toString());
    }
    
    @PostMapping("/search")
    public Page<House> search(@RequestBody String jsonString) {
        return houseService.find(jsonString);
    }
}
