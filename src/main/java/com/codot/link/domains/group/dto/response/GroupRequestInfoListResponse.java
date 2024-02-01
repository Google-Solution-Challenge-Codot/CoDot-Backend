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
public class GroupRequestInfoListResponse {

	private Integer groupRequestCount;
	private List<GroupRequestInfoResponse> groupRequests;

	@Builder(access = AccessLevel.PRIVATE)
	private GroupRequestInfoListResponse(List<GroupRequestInfoResponse> groupRequests) {
		this.groupRequestCount = groupRequests.size();
		this.groupRequests = groupRequests;
	}

	public static GroupRequestInfoListResponse from(List<GroupRequestInfoResponse> groupRequests) {
		return GroupRequestInfoListResponse.builder()
			.groupRequests(groupRequests)
			.build();
	}
}
