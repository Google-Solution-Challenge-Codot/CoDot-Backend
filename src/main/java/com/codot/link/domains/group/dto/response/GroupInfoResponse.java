package com.codot.link.domains.group.dto.response;

import com.codot.link.domains.group.domain.Group;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupInfoResponse {

	private String name;
	private String description;

	@Builder(access = AccessLevel.PRIVATE)
	private GroupInfoResponse(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public static GroupInfoResponse from(Group group) {
		return GroupInfoResponse.builder()
			.name(group.getName())
			.description(group.getDescription())
			.build();
	}
}
