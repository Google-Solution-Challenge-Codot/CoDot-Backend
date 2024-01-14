package com.codot.link.domains.link.domain;

import com.codot.link.common.auditing.BaseEntity;
import com.codot.link.domains.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Link extends BaseEntity {

	@Id
	@Column(name = "link_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_id", nullable = false)
	private User from;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_id", nullable = false)
	private User to;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "connection_point")
	@Enumerated(EnumType.STRING)
	private ConnectionPoint connectionPoint;

	@Builder(access = AccessLevel.PRIVATE)
	private Link(User from, User to, Status status) {
		this.from = from;
		this.to = to;
		this.status = status;
	}

	public static Link of(User from, User to, Status status) {
		return Link.builder()
			.from(from)
			.to(to)
			.status(status)
			.build();
	}

	public void updateStatus(Status status) {
		if (status != null) {
			this.status = status;
		}
	}

	public void updateConnectionPoint(ConnectionPoint connectionPoint) {
		if (connectionPoint != null) {
			this.connectionPoint = connectionPoint;
		}
	}
}
