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
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "house_id")
    private UUID houseId;

    @Column(name = "discount_rate")
    private float discountRate;

    @Column(name = "discount")
    private int discount;

    @Column(name = "created_at")
    private Timestamp createdAt;

}
