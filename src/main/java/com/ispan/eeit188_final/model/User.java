package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "[user]")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", columnDefinition = "varchar(15)", nullable = false)
    private String name;

    @Column(name = "role", columnDefinition = "varchar(15)")
    private String role;

    @Column(name = "gender", columnDefinition = "varchar(10)")
    private String gender;

    @Column(name = "birthday", columnDefinition = "date", nullable = false)
    private Date birthday;

    @Column(name = "phone", columnDefinition = "varchar(15)")
    private String phone;

    @Column(name = "mobile_phone", columnDefinition = "varchar(15)")
    private String mobilePhone;

    @Column(name = "address", columnDefinition = "varchar(50)")
    private String address;

    @Column(name = "email", columnDefinition = "varchar(30)", unique = true, nullable = false)
    private String email;

    @Column(name = "password", columnDefinition = "varchar(max)", nullable = false)
    private String password;

    @Column(name = "about", columnDefinition = "varchar(max)")
    private String about;

    @Column(name = "created_at", columnDefinition = "datetime2")
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "datetime2")
    private Timestamp updatedAt;

    @Column(name = "avatar_base64", columnDefinition = "varchar(max)")
    private String avatarBase64;

    @Lob
    @Column(name = "background_image_blob", columnDefinition = "varbinary(max)")
    private byte[] backgroundImageBlob;

    // Relationship
    // @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade =
    // CascadeType.ALL, orphanRemoval = true)
    // @Builder.Default
    // private List<Cart> carts = new ArrayList<>();

    // @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade =
    // CascadeType.ALL, orphanRemoval = true)
    // @Builder.Default
    // private List<Ticket> tickets = new ArrayList<>();

    // 關聯 TransactionRecord
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<TransactionRecord> transactionRecords = new HashSet<>();

    // @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade =
    // CascadeType.ALL, orphanRemoval = true)
    // @Builder.Default
    // private List<Coupon> coupons = new ArrayList<>();

    // 關聯 房源
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<House> houses = new HashSet<>();

    @OneToMany(mappedBy = "userCollectionId.userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<UserCollection> userCollections = new ArrayList<>();

    // @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade =
    // CascadeType.ALL, orphanRemoval = true)
    // @Builder.Default
    // private List<Discuss> discusses = new ArrayList<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ChatRecord> sentChatRecords = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ChatRecord> receivedChatRecords = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
