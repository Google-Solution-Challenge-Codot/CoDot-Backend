package com.codot.link.domains.group.dto.response;

import com.codot.link.domains.group.domain.GroupUser;
import com.codot.link.domains.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupRequestInfoResponse {

	private Long groupUserId;
	private Long groupId;
	private Long userId;
	private String name;
	private String nickname;

	@Builder(access = AccessLevel.PRIVATE)
	private GroupRequestInfoResponse(Long groupUserId, Long groupId, Long userId, String name, String nickname) {
		this.groupUserId = groupUserId;
		this.groupId = groupId;
		this.userId = userId;
		this.name = name;
		this.nickname = nickname;
	}

	public static GroupRequestInfoResponse from(GroupUser groupUser) {
		User user = groupUser.getUser();
		return GroupRequestInfoResponse.builder()
			.groupUserId(groupUser.getId())
			.groupId(groupUser.getGroup().getId())
			.userId(user.getId())
			.name(user.getName())
			.nickname(user.getNickname())
			.build();
	}
}