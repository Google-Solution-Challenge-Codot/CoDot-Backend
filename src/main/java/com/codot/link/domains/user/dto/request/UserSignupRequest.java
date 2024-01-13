package com.codot.link.domains.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupRequest {

	@NotEmpty(message = "이름은 필수이며 공백 문자열은 허용되지 않습니다.")
	private String name;

	@NotEmpty(message = "이메일은 필수이며 공백 문자열은 허용되지 않습니다.")
	@Email(message = "이메일 형식이 아닙니다.")
	private String email;

	@NotEmpty(message = "닉네임은 필수이며 공백 문자열은 허용되지 않습니다.")
	private String nickname;

	@NotEmpty(message = "대학 이름은 필수이며 공백 문자열은 허용되지 않습니다.")
	private String university;

	@NotEmpty(message = "학과 이름은 필수이며 공백 문자열은 허용되지 않습니다.")
	private String department;

	private String introduction;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private UserSignupRequest(String name, String email, String nickname, String university, String department,
		String introduction) {
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.university = university;
		this.department = department;
		this.introduction = introduction;
	}

	public static UserSignupRequest from(String name, String email, String nickname, String university,
		String department, String introduction) {
		return UserSignupRequest.builder()
			.name(name)
			.email(email)
			.nickname(nickname)
			.university(university)
			.department(department)
			.introduction(introduction)
			.build();
	}
}
