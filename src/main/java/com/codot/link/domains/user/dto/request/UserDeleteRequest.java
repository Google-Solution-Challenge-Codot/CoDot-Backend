package com.codot.link.domains.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDeleteRequest {

	@NotBlank(message = "이메일은 필수이며 공백 문자열은 허용되지 않습니다.")
	@Email(message = "이메일 형식이 아닙니다.")
	private String email;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private UserDeleteRequest(String email) {
		this.email = email;
	}

	public static UserDeleteRequest from(String email) {
		return UserDeleteRequest.builder()
			.email(email)
			.build();
	}
}
