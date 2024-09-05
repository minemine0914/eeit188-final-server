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
@Table(name = "transaction_record")
public class TransactionRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "house_id")
    private UUID houseId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "cash_flow")
    private int cashFlow;

    @Column(name = "deal")
    private String deal;

    @Column(name = "created_at", columnDefinition = "datetime2")
    private Timestamp createdAt;

}
