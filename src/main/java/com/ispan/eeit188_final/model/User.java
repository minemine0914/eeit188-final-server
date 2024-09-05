package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    @Id
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", columnDefinition = "varchar(15)")
    private String name;

    @Column(name = "gender", columnDefinition = "varchar(10)")
    private String gender;

    @Column(name = "age", columnDefinition = "tinyint")
    private Short age;

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
    private Timestamp updatetedAt;

    @Column(name = "headshot_image_base64", columnDefinition = "varchar(max)")
    private String headshotImageBase64;

    @Lob
    @Column(name = "background_image_blob", columnDefinition = "varbinary(max)")
    private byte[] backgroundImageBlob;

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void onUpdate() {
        this.updatetedAt = new Timestamp(System.currentTimeMillis());
    }
}
