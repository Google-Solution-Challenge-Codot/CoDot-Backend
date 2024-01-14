package com.codot.link.domains.link.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FriendRequest {

	@NotNull(message = "요청 보낼 대상의 id값은 필수입니다.")
	@Min(value = 1, message = "id값은 1 이상이어야 합니다.")
	private Long targetId;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private FriendRequest(Long targetId) {
		this.targetId = targetId;
	}

	public static FriendRequest from(Long targetId) {
		return FriendRequest.builder()
			.targetId(targetId)
			.build();
	}
}
