package com.codot.link.domains.group.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupCreateRequest {

	@NotEmpty(message = "그룹 이름은 필수이며 공백 문자열을 허용되지 않습니다.")
	private String name;

	@NotEmpty(message = "그룹 설명은 필수이며 공백 문자열을 허용되지 않습니다.")
	private String description;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private GroupCreateRequest(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public static GroupCreateRequest of(String name, String description) {
		return GroupCreateRequest.builder()
			.name(name)
			.description(description)
			.build();
	}
}
