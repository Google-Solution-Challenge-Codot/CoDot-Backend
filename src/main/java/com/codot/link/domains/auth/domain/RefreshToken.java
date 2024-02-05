package com.codot.link.domains.auth.domain;

import static java.time.LocalDateTime.*;

import java.time.LocalDateTime;

import com.codot.link.common.auditing.BaseEntity;
import com.codot.link.domains.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refresh_token_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private String token;

	@Column(name = "expire_at", nullable = false)
	private LocalDateTime expireAt;

	@Builder(access = AccessLevel.PRIVATE)
	private RefreshToken(User user, String token, LocalDateTime expireAt) {
		this.user = user;
		this.token = token;
		this.expireAt = expireAt;
	}

	public static RefreshToken of(User user, String token) {
		return RefreshToken.builder()
			.user(user)
			.token(token)
			.expireAt(now().plusDays(7))
			.build();
	}
}
