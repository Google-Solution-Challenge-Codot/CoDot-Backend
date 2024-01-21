package com.codot.link.domains.user.dto.request;

import com.codot.link.domains.user.domain.GraduationStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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

	@NotNull(message = "재학 여부는 필수입니다.")
	private GraduationStatus graduationStatus;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private UserSignupRequest(String name, String email, String nickname, String university, String department,
		String introduction, GraduationStatus graduationStatus) {
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.university = university;
		this.department = department;
		this.introduction = introduction;
		this.graduationStatus = graduationStatus;
	}

	public static UserSignupRequest of(String name, String email, String nickname, String university,
		String department, String introduction, GraduationStatus graduationStatus) {
		return UserSignupRequest.builder()
			.name(name)
			.email(email)
			.nickname(nickname)
			.university(university)
			.department(department)
			.introduction(introduction)
			.graduationStatus(graduationStatus)
			.build();
	}
}
