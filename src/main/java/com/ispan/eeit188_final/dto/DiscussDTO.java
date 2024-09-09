package com.ispan.eeit188_final.dto;

import java.sql.Timestamp;
import java.util.UUID;

import com.ispan.eeit188_final.model.Discuss;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DiscussDTO {

    private UUID id;
    private String discuss;
    private Boolean show;
    private Timestamp createdAt;
    private User userId;
    private House houseId;
    private Discuss discussId;
}
