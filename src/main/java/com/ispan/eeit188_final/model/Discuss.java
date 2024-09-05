package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "discuss")
public class Discuss {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "discuss")
    private String discuss;

    @Column(name = "show")
    private boolean show;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "user_id", columnDefinition = "uniqueidentifier")
    private UUID userId;

    @Column(name = "house_id", columnDefinition = "uniqueidentifier")
    private UUID houseId;

    @Column(name = "discuss_id", columnDefinition = "uniqueidentifier")
    private UUID discussId;

}
