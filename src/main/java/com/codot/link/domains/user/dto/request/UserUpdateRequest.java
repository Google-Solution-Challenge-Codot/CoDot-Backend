package com.codot.link.domains.user.dto.request;

import com.codot.link.domains.user.domain.GraduationStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserUpdateRequest {

	private String name;

	@Email(message = "이메일 형식이 아닙니다.")
	private String email;

	private String nickname;

	private String university;

	private String department;

	private String introduction;

	private GraduationStatus graduationStatus;

	private String filePath;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private UserUpdateRequest(String name, String email, String nickname, String university, String department,
		String introduction, GraduationStatus graduationStatus, String filePath) {
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.university = university;
		this.department = department;
		this.introduction = introduction;
		this.graduationStatus = graduationStatus;
		this.filePath = filePath;
	}

	public static UserUpdateRequest of(String name, String email, String nickname, String university,
		String department, String introduction, GraduationStatus graduationStatus, String filePath) {
		return UserUpdateRequest.builder()
			.name(name)
			.email(email)
			.nickname(nickname)
			.university(university)
			.department(department)
			.introduction(introduction)
			.graduationStatus(graduationStatus)
			.filePath(filePath)
			.build();
	}
}
