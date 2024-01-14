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
}
