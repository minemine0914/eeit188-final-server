package com.ispan.eeit188_final.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// import org.thymeleaf.expression.Messages;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "DiscussExternalResource") // 討論區外部資源
public class DiscussExternalResource {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID Id;

    @Column(name = "url", columnDefinition = "VARCHAR(MAX)")
    private String url;

    @Column(name = "discuss_id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID DiscussId;

    @Column(name = "type", columnDefinition = "VARCHAR(10)")
    private String type;

    @Column(name = "created_at", columnDefinition = "DATETIME2")
    private LocalDateTime createdAt;

    // @OneToMany(mappedBy = "DiscussExternalResource", cascade = CascadeType.ALL)
    // private List<House> House;
}
