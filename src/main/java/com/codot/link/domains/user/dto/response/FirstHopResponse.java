package com.codot.link.domains.user.dto.response;

import com.codot.link.domains.link.domain.ConnectionPoint;
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
public class FirstHopResponse extends HopResponse {

	private ConnectionPoint sourceAndFirstHopConnectionPoint;

	@Builder(access = AccessLevel.PRIVATE)
	public FirstHopResponse(Long userId, String filePath, String nickname,
		ConnectionPoint connectionPoint) {
		super(userId, filePath, nickname);
		this.sourceAndFirstHopConnectionPoint = connectionPoint;
	}

	public static FirstHopResponse of(User user, ConnectionPoint connectionPoint) {
		return FirstHopResponse.builder()
			.userId(user.getId())
			.filePath(user.getFilePath())
			.nickname(user.getNickname())
			.connectionPoint(connectionPoint)
			.build();
	}
}
