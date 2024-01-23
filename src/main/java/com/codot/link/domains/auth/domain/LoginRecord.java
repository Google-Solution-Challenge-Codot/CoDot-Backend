package com.codot.link.domains.auth.domain;

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
public class LoginRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "login_record_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "oauth_id", nullable = false)
	private String oauthId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Provider provider;

	@Column(nullable = false)
	private boolean registered;

	@Builder(access = AccessLevel.PRIVATE)
	private LoginRecord(String oauthId, Provider provider) {
		this.oauthId = oauthId;
		this.provider = provider;
		this.user = null;
		this.registered = false;
	}

	public static LoginRecord of(String oauthId, Provider provider) {
		return LoginRecord.builder()
			.oauthId(oauthId)
			.provider(provider)
			.build();
	}

	public boolean isRegistered() {
		return registered;
	}

	public void registerUser(User user) {
		this.user = user;
		this.registered = true;
	}
}
