package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "discuss", indexes = {
        @Index(name = "discuss_show_index", columnList = "show", unique = false),
        @Index(name = "discuss_created_at_index", columnList = "created_at", unique = false),
        @Index(name = "discuss_user_id_index", columnList = "user_id", unique = false),
        @Index(name = "discuss_house_id_index", columnList = "house_id", unique = false) })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Discuss {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "discuss", columnDefinition = "nvarchar(max)")
    private String discuss;

    @Column(name = "show", columnDefinition = "bit")
    private Boolean show;

    @Column(name = "created_at", columnDefinition = "datetime2")
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "datetime2")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "uniqueidentifier")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", columnDefinition = "uniqueidentifier")
    private House house;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discuss_id", columnDefinition = "uniqueidentifier")
    private Discuss subDiscuss;

    // Relationshp
    @OneToMany(mappedBy = "subDiscuss", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Discuss> discusses = new ArrayList<>();

    @OneToMany(mappedBy = "discuss", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DiscussExternalResource> discussExternalResources = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
