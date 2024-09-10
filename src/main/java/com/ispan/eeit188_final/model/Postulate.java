package com.ispan.eeit188_final.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "postulate")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Postulate {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private UUID id;

	@Column(name = "name", columnDefinition = "nvarchar(15)")
	private String name;

	@Column(name = "icon", columnDefinition = "nvarchar(50)")
	private String icon;

	@Column(name = "created_at", columnDefinition = "datetime2")
	private Timestamp createdAt;

	@ManyToMany(mappedBy = "postulates", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JsonIgnore
	@Builder.Default
	private List<House> houses = new ArrayList<>();

	@PrePersist
	public void onCreate() {
		this.createdAt = new Timestamp(System.currentTimeMillis());
	}

}
