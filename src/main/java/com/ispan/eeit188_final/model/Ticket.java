package com.ispan.eeit188_final.model;

import java.util.UUID;
import java.sql.timestamp;
import jarkarta.persistence.*;


@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "house_id")
    private UUID houseId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "started_at")
    private timestamp startedAt;

    @Column(name = "ended_at")
    private timestamp endedAt;

    @Column(name = "created_at")
    private timestamp createdAt;

}
