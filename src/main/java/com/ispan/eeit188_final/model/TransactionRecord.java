package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction_record")
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    // @Column(name = "house_id", columnDefinition = "uniqueidentifier")
    // private UUID houseId;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", columnDefinition = "uniqueidentifier")
    private House houseId;


    //@Column(name = "user_id", columnDefinition = "uniqueidentifier")
    //private UUID userId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "uniqueidentifier")
    private User userId;
    

    @Column(name = "cash_flow", columnDefinition = "int")
    private int cashFlow;

    @Column(name = "deal", columnDefinition = "varchar(10)")
    private String deal;

    @Column(name = "created_at", columnDefinition = "datetime2")
    private Timestamp createdAt;

}
