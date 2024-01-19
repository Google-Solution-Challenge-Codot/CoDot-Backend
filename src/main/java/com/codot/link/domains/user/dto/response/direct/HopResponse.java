package com.codot.link.domains.user.dto.response.direct;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class HopResponse {

	private Long userId;
	private String filePath;
	private String nickname;

	protected HopResponse(Long userId, String filePath, String nickname) {
		this.userId = userId;
		this.filePath = filePath;
		this.nickname = nickname;
	}
}
