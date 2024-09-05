package com.ispan.eeit188_final.model;

import java.time.LocalDateTime;
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
@Setter
@Getter
@Entity
@Table(name = "Housed_External_Resource") // 地點外部資源
public class Housed_External_Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", columnDefinition = "UUID")
    private UUID Id;

    @Column(name = "house_id", columnDefinition = "UUID")
    private UUID HouseId;

    @Column(name = "url", columnDefinition = "VARCHAR(MAX)")
    private String url;

    @Column(name = "type", columnDefinition = "VARCHAR(10)")
    private String type;

    @Column(name = "created_at", columnDefinition = "DATETIME2")
    private LocalDateTime createdAt;

}
