package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
public class User {

    // Data
    @Id
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", columnDefinition = "varchar(15)", nullable = false)
    private String name;

    @Column(name = "gender", columnDefinition = "varchar(10)")
    private String gender;

    @Column(name = "birthday", columnDefinition = "date")
    private Date birthday;

    @Column(name = "phone", columnDefinition = "varchar(15)")
    private String phone;

    @Column(name = "mobile_phone", columnDefinition = "varchar(15)")
    private String mobilePhone;

    @Column(name = "address", columnDefinition = "varchar(50)")
    private String address;

    @Column(name = "email", columnDefinition = "varchar(30)", unique = true, nullable = false)
    private String email;

    @Column(name = "password", columnDefinition = "varchar(30)", nullable = false)
    private String password;

    @Column(name = "about", columnDefinition = "varchar(max)")
    private String about;

    @Column(name = "created_at", columnDefinition = "datetime2")
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "datetime2")
    private Timestamp updatedAt;

    @Column(name = "headshot_image_base64", columnDefinition = "varchar(max)")
    private String headshotImageBase64;

    @Lob
    @Column(name = "background_image_blob", columnDefinition = "varbinary(max)")
    private byte[] backgroundImageBlob;

    // Relationship
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionRecord> transactionRecords;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coupon> coupons;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<House> houses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCollection> userCollections;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Discuss> discusses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRecord> chatRecords;

    // Methods
    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
