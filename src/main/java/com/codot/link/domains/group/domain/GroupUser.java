package com.codot.link.domains.group.domain;

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
public class GroupUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_user_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(nullable = false)
	private boolean accepted;

	@Builder(access = AccessLevel.PRIVATE)
	private GroupUser(User user, Group group, Role role, boolean accepted) {
		this.user = user;
		this.group = group;
		this.role = role;
		this.accepted = accepted;
	}

	public static GroupUser of(User user, Group group, Role role, boolean accepted) {
		return GroupUser.builder()
			.user(user)
			.group(group)
			.role(role)
			.accepted(accepted)
			.build();
	}

	public void joinGroup() {
		this.accepted = true;
	}
}
