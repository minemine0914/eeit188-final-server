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
@Table(name = "coupon")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "discount_rate", columnDefinition = "real")
    private Double discountRate;

    @Column(name = "discount", columnDefinition = "int")
    private Integer discount;

    @Column(name = "expire", columnDefinition = "int")
    private Integer expire;

    @Column(name = "created_at", columnDefinition = "datetime2")
    private Timestamp createdAt;

    // 與 House 的關聯
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNIQUEIDENTIFIER")
    // @JsonIgnore
    // private House house;

    // 與 User 的關聯
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNIQUEIDENTIFIER")
    @JsonIgnore
    private User user;

    // 自訂序列化 userName
    @JsonProperty("userName")
    public String userName() {
        return user != null ? user.getName() : null;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

}
