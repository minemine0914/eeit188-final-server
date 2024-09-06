package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "chat_record")
public class ChatRecord {

    @Id
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "chat", columnDefinition = "varchar(max)")
    private String chat;

    @Column(name = "show", columnDefinition = "bit")
    private Boolean show;

    @Column(name = "sender_id", columnDefinition = "uniqueidentifier")
    private UUID senderId;

    @Column(name = "receiver_id", columnDefinition = "uniqueidentifier")
    private UUID receiverId;

    @Column(name = "platform_staff_id", columnDefinition = "uniqueidentifier")
    private UUID platformStaffId;

    @Column(name = "created_at", columnDefinition = "datetime2")
    private Timestamp createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
