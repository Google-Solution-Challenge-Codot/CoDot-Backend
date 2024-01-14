package com.codot.link.domains.user.domain;

import com.codot.link.common.auditing.BaseEntity;
import com.codot.link.domains.user.dto.request.UserSignupRequest;
import com.codot.link.domains.user.dto.request.UserUpdateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false, unique = true)
	private String nickname;

	@Column(nullable = false)
	private String university;

	@Column(nullable = false)
	private String department;

	@Column(nullable = false)
	private String introduction;

	@Column(name = "graduation_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private GraduationStatus graduationStatus;

	@Column(name = "file_path", nullable = false)
	private String filePath;

	@Builder(access = AccessLevel.PRIVATE)
	private User(String name, String email, String nickname, String university, String department,
		String introduction, GraduationStatus graduationStatus) {
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.university = university;
		this.department = department;
		this.introduction = introduction;
		this.graduationStatus = graduationStatus;
		this.filePath = "default file path";
	}

	public static User from(UserSignupRequest request) {
		String introduction = (request.getIntroduction() == null) ? "자기 소개" : request.getIntroduction();

		return User.builder()
			.name(request.getName())
			.email(request.getEmail())
			.nickname(request.getNickname())
			.university(request.getUniversity())
			.department(request.getDepartment())
			.introduction(introduction)
			.graduationStatus(request.getGraduationStatus())
			.build();
	}

	public void updateInfo(UserUpdateRequest request) {
		if (request.getName() != null) {
			name = request.getName();
		}
		if (request.getEmail() != null) {
			email = request.getEmail();
		}
		if (request.getNickname() != null) {
			nickname = request.getNickname();
		}
		if (request.getUniversity() != null) {
			university = request.getUniversity();
		}
		if (request.getDepartment() != null) {
			department = request.getDepartment();
		}
		if (request.getIntroduction() != null) {
			introduction = request.getIntroduction();
		}
		if (request.getGraduationStatus() != null) {
			graduationStatus = request.getGraduationStatus();
		}
		if (request.getFilePath() != null) {
			filePath = request.getFilePath();
		}
	}
}
