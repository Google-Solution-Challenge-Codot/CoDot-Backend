package com.codot.link.domains.group.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupSearchListResponse {

	private Integer groupCount;
	private List<GroupSearchResponse> groups;

	@Builder(access = AccessLevel.PRIVATE)
	private GroupSearchListResponse(List<GroupSearchResponse> groups) {
		this.groupCount = groups.size();
		this.groups = groups;
	}

	public static GroupSearchListResponse from(List<GroupSearchResponse> groups) {
		return GroupSearchListResponse.builder()
			.groups(groups)
			.build();
	}
}
