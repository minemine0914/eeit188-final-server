package com.ispan.eeit188_final.dto;

import java.sql.Timestamp;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceRangeDTO {
    private UUID id;
    private Integer newPrice;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private UUID houseId;
}
