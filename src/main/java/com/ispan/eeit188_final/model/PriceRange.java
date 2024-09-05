package com.ispan.eeit188_final.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class PriceRange {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "new_price")
    private Integer newPrice;       // 浮動價錢

    @Column(name = "house_id")
    private String houseId;         // 房源ID (UUID)

    // 開始/結束 區間
    @Column(name = "started_at")
    private Timestamp startedAt;    // 起始時間
    @Column(name = "ended_at")
    private Timestamp endedAt;      // 結束時間

    // 建立/修改 時間
    @Column(name = "create_at")
    private Timestamp createdAt;     // 建立時間
    @Column(name = "update_at")
    private Timestamp updatedAt;     // 修改時間

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
