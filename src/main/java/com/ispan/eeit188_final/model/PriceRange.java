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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "price_range")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PriceRange {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID id;

    @Column(name = "new_price", columnDefinition = "INT")
    private Integer newPrice; // 浮動價錢

    // 開始/結束 區間
    @Column(name = "started_at", columnDefinition = "DATETIME2")
    private Timestamp startedAt; // 起始時間
    @Column(name = "ended_at", columnDefinition = "DATETIME2")
    private Timestamp endedAt; // 結束時間

    // 建立/修改 時間
    @Column(name = "created_at", columnDefinition = "DATETIME2")
    private Timestamp createdAt; // 建立時間
    @Column(name = "updated_at", columnDefinition = "DATETIME2")
    private Timestamp updatedAt; // 修改時間

    // 關聯 house.id = price_range.house_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", columnDefinition = "UNIQUEIDENTIFIER")
    @JsonIgnore
    private House house; // 房源

    // 自訂序列化 houseId
    @JsonProperty("houseId")
    public UUID houseId() {
        return house != null ? house.getId() : null;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

}
