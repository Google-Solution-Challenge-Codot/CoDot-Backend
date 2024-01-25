package com.codot.link.domains.group.dto.response;

import com.codot.link.domains.group.dao.MyGroupResult;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MyGroupResponse {

	private Long groupId;
	private String groupName;

	@Builder(access = AccessLevel.PRIVATE)
	private MyGroupResponse(Long groupId, String groupName) {
		this.groupId = groupId;
		this.groupName = groupName;
	}

	public static MyGroupResponse from(MyGroupResult result) {
		return MyGroupResponse.builder()
			.groupId(result.getGroupId())
			.groupName(result.getGroupName())
			.build();
	}
}
