package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "user_id", columnDefinition = "uniqueidentifier")
    private UUID userId;

    @Column(name = "house_id", columnDefinition = "uniqueidentifier")
    private UUID houseId;

    @Column(name = "discount_rate", columnDefinition = "float(10)")
    private float discountRate;

    @Column(name = "discount", columnDefinition = "int")
    private int discount;

    @Column(name = "expire", columnDefinition = "int")
    private int expire;

    @Column(name = "created_at", columnDefinition = "datetime2")
    private Timestamp createdAt;

}
