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
public class FriendListResponse {

	private Integer friendsCount;
	private List<FriendResponse> friends;

	@Builder(access = AccessLevel.PRIVATE)
	private FriendListResponse(List<FriendResponse> friends) {
		this.friendsCount = friends.size();
		this.friends = friends;
	}

	public static FriendListResponse from(List<FriendResponse> friends) {
		return FriendListResponse.builder()
			.friends(friends)
			.build();
	}
}
