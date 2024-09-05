package com.ispan.eeit188_final.model;

import java.util.UUID;
import java.sql.timestamp;
import jarkarta.persistence.*;

@Entity
@Table(name = "transaction_record")
public class TransactionRecord {
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "house_id", nullable = false)
    private UUID houseId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "cash_flow")
    private int cashFlow;

    @Column(name = "deal", length = 10)
    private String deal;

    @Column(name = "created_at")
    private timestamp createdAt;

}
