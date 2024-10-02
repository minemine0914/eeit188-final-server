package com.ispan.eeit188_final.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.ispan.eeit188_final.dto.HouseDTO;
import com.ispan.eeit188_final.model.House;

import java.util.UUID;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class HouseTests {
    @Autowired
    private HouseService houseService;

    // @Test
    void findById() {
        UUID id = UUID.fromString("6ce2346f-4768-4179-8e15-67a5de76fc96");
        System.out.println("House: " + houseService.findById(id));
    }

    // @Test
    void create() {
        // House insert = House.builder().name("testtest").build();
        // houseService.create(insert);
    }

    // @Test
    void find() {
        HouseDTO query = HouseDTO.builder()
        .page(0)
        .limit(10)
        .build();
        Page<House> result = houseService.find(query);
        System.out.println(result.getContent());

    }
}
