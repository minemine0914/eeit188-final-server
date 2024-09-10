package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ChatRecord {

    // Data
    @Id
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "chat", columnDefinition = "nvarchar(max)")
    private String chat;

    @Column(name = "show", columnDefinition = "bit")
    private Boolean show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", columnDefinition = "uniqueidentifier")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", columnDefinition = "uniqueidentifier")
    private User receiver;

    @Column(name = "created_at", columnDefinition = "datetime2")
    private Timestamp createdAt;

    // Relationship
    @OneToMany(mappedBy = "chatRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ChatExternalResource> chatExternalResources = new ArrayList<>();

    // Methods
    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
