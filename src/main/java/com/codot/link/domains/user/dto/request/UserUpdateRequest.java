package com.codot.link.domains.user.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequest {

	private String name;

	@Email(message = "이메일 형식이 아닙니다.")
	private String email;

	private String nickname;

	private String university;

	private String department;

	private String introduction;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private UserUpdateRequest(String name, String email, String nickname, String university, String department,
		String introduction) {
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.university = university;
		this.department = department;
		this.introduction = introduction;
	}

	public static UserUpdateRequest from(String name, String email, String nickname, String university,
		String department, String introduction) {
		return UserUpdateRequest.builder()
			.name(name)
			.email(email)
			.nickname(nickname)
			.university(university)
			.department(department)
			.introduction(introduction)
			.build();
	}
}
