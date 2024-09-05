package com.ispan.eeit188_final;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ispan.eeit188_final.service.HouseService;
import java.util.UUID;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class HouseTests {
    @Autowired
    private HouseService houseService;

    @Test
    void findById() {
        UUID id = UUID.fromString("6ce2346f-4768-4179-8e15-67a5de76fc96");
        System.out.println("House: " + houseService.findById(id));
    }
}
