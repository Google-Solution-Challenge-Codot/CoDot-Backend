package com.codot.link.domains.user.dto.response.friend;

import java.util.List;

import com.codot.link.domains.link.domain.ConnectionPoint;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FriendSearchListResponse {

	private ConnectionPoint sourceToTargetConnectionPoint;
	private ConnectionPoint targetToSourceConnectionPoint;
	private Integer friendCount;
	private List<FriendSearchResponse> friends;

	@Builder(access = AccessLevel.PRIVATE)
	private FriendSearchListResponse(List<FriendSearchResponse> friends, ConnectionPoint sourceToTargetConnectionPoint,
		ConnectionPoint targetToSourceConnectionPoint) {
		this.friendCount = friends.size();
		this.friends = friends;
		this.sourceToTargetConnectionPoint = sourceToTargetConnectionPoint;
		this.targetToSourceConnectionPoint = targetToSourceConnectionPoint;
	}

	public static FriendSearchListResponse of(List<FriendSearchResponse> friends,
		ConnectionPoint sourceToTargetConnectionPoint,
		ConnectionPoint targetToSourceConnectionPoint) {
		return FriendSearchListResponse.builder()
			.friends(friends)
			.sourceToTargetConnectionPoint(sourceToTargetConnectionPoint)
			.targetToSourceConnectionPoint(targetToSourceConnectionPoint)
			.build();
	}
}
