package com.ispan.eeit188_final.model;

import java.util.UUID;
import java.sql.timestamp;
import jarkarta.persistence.*;

@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue
    @Column(name = "id")
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
    private timestamp createdAt;

}
