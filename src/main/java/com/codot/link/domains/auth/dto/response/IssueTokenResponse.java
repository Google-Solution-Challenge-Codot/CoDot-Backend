package com.codot.link.domains.auth.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class IssueTokenResponse {

	private IdResponse idInfo;
	private String accessToken;
	private String refreshToken;

	@Builder(access = AccessLevel.PRIVATE)
	private IssueTokenResponse(IdResponse idInfo, String accessToken, String refreshToken) {
		this.idInfo = idInfo;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static IssueTokenResponse of(IdResponse idInfo, String accessToken, String refreshToken) {
		return IssueTokenResponse.builder()
			.idInfo(idInfo)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
