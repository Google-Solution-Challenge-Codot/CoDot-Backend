package com.codot.link.domains.user.domain;

import com.codot.link.common.auditing.BaseEntity;
import com.codot.link.domains.user.dto.request.UserSignupRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String email;

	private String nickname;

	private String university;

	private String department;

	private String introduction;

	@Builder(access = AccessLevel.PRIVATE)
	private User(String name, String email, String nickname, String university, String department,
		String introduction) {
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.university = university;
		this.department = department;
		this.introduction = introduction;
	}

	public static User from(UserSignupRequest request) {
		String introduction = (request.getIntroduction() == null) ? "" : request.getIntroduction();

		return User.builder()
			.name(request.getName())
			.email(request.getEmail())
			.nickname(request.getNickname())
			.university(request.getUniversity())
			.department(request.getDepartment())
			.introduction(introduction)
			.build();
	}
}
