package com.ispan.eeit188_final.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Platform_Staff") // 平台方工作人員
public class Platform_Staff {

    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "name", columnDefinition = "VARCHAR(30)")
    private String name;

    @Column(name = "role", columnDefinition = "VARCHAR(15)")
    private String role;

    @Column(name = "gender", columnDefinition = "VARCHAR(10)")
    private String gender;

    @Column(name = "age", columnDefinition = "TINYINT")
    private Byte age;

    @Column(name = "phone", columnDefinition = "VARCHAR(15)")
    private String phone;

    @Column(name = "mobile_phone", columnDefinition = "VARCHAR(15)")
    private String mobile_phone;

    @Column(name = "address", columnDefinition = "VARCHAR(50)")
    private String address;

    @Column(name = "email", columnDefinition = "VARCHAR(30)", nullable = false)
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(30)", nullable = false)
    private String password;

    @Column(name = "about", columnDefinition = "VARCHAR(max)")
    private String about;

    @Column(name = "created_at", columnDefinition = "DATETIME2")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME2")
    private LocalDateTime updatedAt;

    @JoinColumn(name = "manager_id", columnDefinition = "UUID")
    private UUID manager_id;

    @Column(name = "headshot_image_base64", columnDefinition = "VARCHAR(MAX)")
    private String headshotImageBase64;

    @Lob
    @Column(name = "background_image_blob", columnDefinition = "BLOB")
    private byte[] backgroundImageBlob;

}
