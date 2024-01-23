package com.codot.link.domains.auth.dto.request;

import com.codot.link.domains.auth.domain.Provider;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class IssueTokenRequest {

	@NotEmpty(message = "oauthId 값은 필수이며 공백 문자열은 허용되지 않습니다.")
	private String oauthId;

	@NotNull(message = "oauth provider는 필수입니다.")
	private Provider provider;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private IssueTokenRequest(String oauthId, Provider provider) {
		this.oauthId = oauthId;
		this.provider = provider;
	}

	public static IssueTokenRequest of(String oauthId, Provider provider) {
		return IssueTokenRequest.builder()
			.oauthId(oauthId)
			.provider(provider)
			.build();
	}
}
