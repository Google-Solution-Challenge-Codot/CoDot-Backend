package com.codot.link.domains.link.dto.response;

import com.codot.link.domains.link.domain.Link;
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
public class FriendResponse {

	private Long linkId;
	private Long userId;
	private String filePath;
	private String name;
	private String nickname;

	@Builder(access = AccessLevel.PRIVATE)
	private FriendResponse(Long linkId, Long userId, String filePath, String name, String nickname) {
		this.linkId = linkId;
		this.userId = userId;
		this.filePath = filePath;
		this.name = name;
		this.nickname = nickname;
	}

	public static FriendResponse from(Link link) {
		User friend = link.getTo();
		return FriendResponse.builder()
			.linkId(link.getId())
			.userId(friend.getId())
			.filePath(friend.getFilePath())
			.name(friend.getName())
			.nickname(friend.getNickname())
			.build();
	}
}
