package com.codot.link.domains.group.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupDeleteRequest {

	@NotEmpty(message = "이메일은 필수이며 공백 문자열은 허용되지 않습니다.")
	@Email(message = "이메일 형식이 아닙니다.")
	private String email;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private GroupDeleteRequest(String email) {
		this.email = email;
	}

	public static GroupDeleteRequest from(String email) {
		return GroupDeleteRequest.builder()
			.email(email)
			.build();
	}
}
