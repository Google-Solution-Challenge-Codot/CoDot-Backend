package com.codot.link.domains.group.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupModifyRequest {

	private String name;

	private String description;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private GroupModifyRequest(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public static GroupModifyRequest of(String name, String description) {
		return GroupModifyRequest.builder()
			.name(name)
			.description(description)
			.build();
	}
}
