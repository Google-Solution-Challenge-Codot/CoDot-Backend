package com.codot.link.domains.user.dto.response;

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
public class TwoHopResponse {

	private String filePath;
	private Long twoHopUserId;
	private Long bridgeUserId;
	private String name;
	private String nickname;

	@Builder(access = AccessLevel.PRIVATE)
	private TwoHopResponse(String filePath, Long twoHopUserId, Long bridgeUserId, String name, String nickname) {
		this.filePath = filePath;
		this.twoHopUserId = twoHopUserId;
		this.bridgeUserId = bridgeUserId;
		this.name = name;
		this.nickname = nickname;
	}

	public static TwoHopResponse from(Link link) {
		User user = link.getTo();
		return TwoHopResponse.builder()
			.filePath(user.getFilePath())
			.twoHopUserId(user.getId())
			.bridgeUserId(link.getFrom().getId())
			.name(user.getName())
			.nickname(user.getNickname())
			.build();
	}
}
