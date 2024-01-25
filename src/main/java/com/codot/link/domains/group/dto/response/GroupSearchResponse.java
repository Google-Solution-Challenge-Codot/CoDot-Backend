package com.codot.link.domains.group.dto.response;

import com.codot.link.domains.group.domain.Group;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupSearchResponse {

	private Long groupId;
	private String name;
	private String description;

	@Builder(access = AccessLevel.PRIVATE)
	private GroupSearchResponse(Long groupId, String name, String description) {
		this.groupId = groupId;
		this.name = name;
		this.description = description;
	}

	public static GroupSearchResponse from(Group group) {
		return GroupSearchResponse.builder()
			.groupId(group.getId())
			.name(group.getName())
			.description(group.getDescription())
			.build();
	}
}
