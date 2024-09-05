package com.ispan.eeit188_final.model;

import java.util.UUID;
import java.sql.timestamp;
import jarkarta.persistence.*;

@Entity
@Table(name = "discuss")
public class Discuss {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "discuss", length = Integer.MAX_VALUE)
    private String discuss;

    @Column(name = "show")
    private boolean show;

    @Column(name = "created_at", index = true)
    private timestamp createdAt;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "house_id", nullable = false)
    private UUID houseId;

    @Column(name = "discuss_id", nullable = false)
    private UUID discussId;

}
