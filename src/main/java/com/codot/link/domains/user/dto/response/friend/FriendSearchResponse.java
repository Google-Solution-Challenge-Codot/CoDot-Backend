package com.codot.link.domains.user.dto.response.friend;

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
public class FriendSearchResponse {

	private Long userId;
	private String filePath;
	private String nickname;

	@Builder(access = AccessLevel.PRIVATE)
	private FriendSearchResponse(Long userId, String filePath, String nickname) {
		this.userId = userId;
		this.filePath = filePath;
		this.nickname = nickname;
	}
	
	public static FriendSearchResponse from(User user) {
		return FriendSearchResponse.builder()
			.userId(user.getId())
			.filePath(user.getFilePath())
			.nickname(user.getNickname())
			.build();
	}
}
