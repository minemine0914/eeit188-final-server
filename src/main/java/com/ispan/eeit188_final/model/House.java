package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.UUID;

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
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    // 房源基本資訊
    @Column(name = "name")
    private String name;            // 房源名稱
    @Column(name = "category")
    private String category;        // 類型
    @Column(name = "information")
    private String information;     // 詳細資訊
    @Column(name = "latitude_x")
    private Double latitudeX;       // 經度
    @Column(name = "longitude_y")
    private Double longitudeY;      // 緯度
    @Column(name = "country")
    private String country;         // 國家
    @Column(name = "city")
    private String city;            // 縣市
    @Column(name = "region")
    private String region;          // 區域
    @Column(name = "address")
    private String address;         // 地址
    @Column(name = "price")
    private Integer price;

    // 房源基本設施 幾廳 幾房 幾衛 幾浴 (TinyInt)
    @Column(name = "living_dining_room", columnDefinition = "TINYINT")
    private Short livingDiningRoom; // 客廳
    @Column(name = "bedroom", columnDefinition = "TINYINT")
    private Short bedroom;          // 房間
    @Column(name = "restroom", columnDefinition = "TINYINT")
    private Short restroom;         // 衛生間 (馬桶)
    @Column(name = "bathroom", columnDefinition = "TINYINT")
    private Short bathroom;         // 淋浴間

    // 常態設施 (True/False)
    @Column(name = "kitchen")
    private Boolean kitchen;        // 廚房
    @Column(name = "balcony")
    private Boolean balcony;        // 陽台

    // 狀態
    @Column(name = "show")
    private Boolean show;           // 是否刊登顯示
    @Column(name = "user_id")
    private String userId;          // 擁有者ID (UUID)

    // 建立/修改 時間
    @Column(name = "create_at")
    private Timestamp createdAt;     // 建立時間
    @Column(name = "update_at")
    private Timestamp updatedAt;     // 修改時間

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        // this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
