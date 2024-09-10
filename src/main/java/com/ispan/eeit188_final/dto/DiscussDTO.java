package com.ispan.eeit188_final.dto;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ispan.eeit188_final.model.Discuss;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(value = Include.NON_NULL)
public class DiscussDTO {

    private UUID id;
    private String discuss;
    private Boolean show;
    private Timestamp createdAt;
    private UUID userId;
    private UUID houseId;
    private UUID discussId;

    // Relationship
    private User user;
    private House house;
    private Discuss subDiscuss;

    // error handler
    @Builder.Default
    private String userIdNullException = "{\"message: \"User ID can't be null or empty\"}";

    @Builder.Default
    private String houseIdNullException = "{\"message: \"House ID can't be null or empty\"}";

    @Builder.Default
    private String userNotFoundException = "{\"message: \"User not found\"}";

    @Builder.Default
    private String houseNotFoundException = "{\"message: \"House not found\"}";

    @Builder.Default
    private String discussNotFoundException = "{\"message: \"Discuss not found\"}";
}
