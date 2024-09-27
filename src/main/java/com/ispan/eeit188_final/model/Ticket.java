package com.ispan.eeit188_final.model;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ticket", indexes = {
		@Index(name = "ticket_house_id_index", columnList = "house_id", unique = false),
		@Index(name = "ticket_user_id_index", columnList = "user_id", unique = false)
})
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private UUID id;

	@Column(name = "qr_code", columnDefinition = "nvarchar(max)")
	private String qrCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNIQUEIDENTIFIER")
	// @JsonBackReference
	private User user;
	// @Column(name = "user_id", columnDefinition = "uniqueidentifier")
	// private UUID userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNIQUEIDENTIFIER")
	// @JsonBackReference
	private House house;
	// @Column(name = "house_id", columnDefinition = "uniqueidentifier")
	// private UUID houseId;

	@Column(name = "started_at", columnDefinition = "datetime2")
	private Timestamp startedAt;

	@Column(name = "ended_at", columnDefinition = "datetime2")
	private Timestamp endedAt;

	@Column(name = "used", columnDefinition = "bit")
	private Boolean used;

	@Column(name = "created_at", columnDefinition = "datetime2")
	private Timestamp createdAt;

	@PrePersist
	public void onCreate() {
		this.createdAt = new Timestamp(System.currentTimeMillis());
	}

	@Override
	public String toString() {
		// 自動抓出所有屬性
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(this.getClass().getName());
		result.append(" {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		// print field names paired with their values
		for (Field field : fields) {
			// 不顯示spring產生的屬性
			if (field.getName().indexOf("$$_hibernate") != -1) {
				continue;
			}
			result.append("  ");
			result.append(field.getName());
			result.append(": ");
			// requires access to private field:
			try {
				result.append(field.get(this));
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();

		// return String.format("[id=%s, qrCode=%s, userId=%s, houseId=%s, startedAt=%s,
		// endedAt=%s, createdAt=%s]",
		// id.toString(), qrCode, userId.toString(), houseId.toString(),
		// startedAt.toString(), endedAt.toString(), createdAt.toString());
	}

}
