package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaction_record", indexes = {
        @Index(name = "transaction_record_house_id_index", columnList = "house_id", unique = false),
        @Index(name = "transaction_record_user_id_index", columnList = "user_id", unique = false),
        @Index(name = "transaction_record_created_at_index", columnList = "created_at", unique = false) })
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "id")
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "cash_flow", columnDefinition = "int")
    private Integer cashFlow;

    @Column(name = "deal", columnDefinition = "nvarchar(10)")
    private String deal;

    @Column(name = "platform_income", columnDefinition = "int")
    private Integer platformIncome;

    @Column(name = "created_at", columnDefinition = "datetime2")
    private Timestamp createdAt;

    // 與 House 的關聯
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNIQUEIDENTIFIER")
    // @JsonIgnore
    private House house;

    // 與 User 的關聯
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNIQUEIDENTIFIER")
    // @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "transactionRecord", fetch = FetchType.LAZY)
    private Ticket ticket;

    // 自訂序列化 userName
    @JsonProperty("userGender")
    public String userGender() {
        return user != null ? user.getGender() : null;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

}
