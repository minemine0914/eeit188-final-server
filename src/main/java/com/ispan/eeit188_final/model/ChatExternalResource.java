package com.ispan.eeit188_final.model;

import java.time.LocalDateTime;
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
@Table(name = "ChatExternalResource") // 線上客服外部資源
public class ChatExternalResource {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID Id;

    @Column(name = "url", columnDefinition = "VARCHAR(MAX)")
    private String url;

    @Column(name = "type", columnDefinition = "VARCHAR(10)")
    private String type;

    @Column(name = "created_at", columnDefinition = "DATETIME2")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "ChatRecord_id", insertable = false, updatable = false)
    private ChatRecord chatRecord;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}
