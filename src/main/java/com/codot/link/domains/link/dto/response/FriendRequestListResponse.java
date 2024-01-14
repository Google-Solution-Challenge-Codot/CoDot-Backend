package com.codot.link.domains.link.dto.response;

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
public class FriendRequestListResponse {

	private Integer friendRequestsCount;
	private List<FriendRequestResponse> friendRequests;

	@Builder(access = AccessLevel.PRIVATE)
	private FriendRequestListResponse(List<FriendRequestResponse> friendRequests) {
		this.friendRequestsCount = friendRequests.size();
		this.friendRequests = friendRequests;
	}

	public static FriendRequestListResponse from(List<FriendRequestResponse> friendRequests) {
		return FriendRequestListResponse.builder()
			.friendRequests(friendRequests)
			.build();
	}
}
