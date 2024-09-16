package com.ispan.eeit188_final.model;

import java.time.LocalDateTime;
// import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "HousedExternalResource") // 地點外部資源
public class HousedExternalResource {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID Id;

    @Column(name = "house_id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID HouseId;

    @Column(name = "url", columnDefinition = "NVARCHAR(MAX)")
    private String url;

    @Column(name = "image", columnDefinition = "varbinary(max)")
    private byte[] image;

    @Column(name = "type", columnDefinition = "NVARCHAR(10)") // 類型
    private String type;

    @Column(name = "created_at", columnDefinition = "DATETIME2")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "House_id", insertable = false, updatable = false)
    private House house;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
