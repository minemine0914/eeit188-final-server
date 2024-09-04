package com.ispan.eeit188_final.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Chat_External_Resource") // 線上客服外部資源
public class Chat_External_Resource {

    @Column(name = "ur", columnDefinition = "VARCHAR(MAX)")
    private String ur;

    @Column(name = "chat_record_id", columnDefinition = "UUID")
    private UUID ChatRecordId;

    @Column(name = "type", columnDefinition = "VARCHAR(10)")
    private String type;

    @Column(name = "created_at", columnDefinition = "DATETIME2")
    private LocalDateTime createdAt;

    public Chat_External_Resource() {
    }

}
