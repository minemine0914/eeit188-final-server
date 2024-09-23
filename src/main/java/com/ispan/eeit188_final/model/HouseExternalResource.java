package com.ispan.eeit188_final.model;

// import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

// import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "HouseExternalResource", indexes = {
        @Index(name = "HouseExternalResource_house_id_index", columnList = "house_id", unique = false),
        @Index(name = "HouseExternalResource_type_index", columnList = "type", unique = false),
        @Index(name = "HouseExternalResource_created_at_index", columnList = "created_at", unique = false)
}) // 地點外部資源
public class HouseExternalResource {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID id;

    // @Column(name = "house_id", columnDefinition = "UNIQUEIDENTIFIER")
    // private UUID HouseId;

    @Column(name = "url", columnDefinition = "NVARCHAR(MAX)")
    private String url;

    @Lob
    @Column(name = "image", columnDefinition = "varbinary(max)")
    @JsonIgnore
    private byte[] image;

    @Column(name = "type", columnDefinition = "NVARCHAR(10)") // 類型
    private String type;

    @Column(name = "created_at", columnDefinition = "DATETIME2")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNIQUEIDENTIFIER")
    @JsonIgnore
    private House house;

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
