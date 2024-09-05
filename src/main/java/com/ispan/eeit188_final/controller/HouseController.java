package com.ispan.eeit188_final.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/find/{id}")
    public House findById(@PathVariable String id) {
        
        return houseService.findById(UUID.fromString(id));
    }
    
    @PostMapping("/find")
    public Page<House> findQuery(@RequestBody String jsonString) {
        return houseService.find(jsonString);
    }
    
}
