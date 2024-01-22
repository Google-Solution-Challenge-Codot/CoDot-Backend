package com.codot.link.domains.user.dto.request;

import java.util.List;

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
public class FriendSearchRequest {

	@NotNull(message = "chosenUserId 값은 필수입니다.")
	@Min(value = 1, message = "id값은 1 이상이어야 합니다.")
	private Long chosenUserId;

	@NotNull(message = "previousHops 값은 필수입니다. 만약, 이전 hop이 없다면 빈 배열을 넣어주세요.")
	private List<Long> previousHops;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private FriendSearchRequest(Long chosenUserId, List<Long> previousHops) {
		this.chosenUserId = chosenUserId;
		this.previousHops = previousHops;
	}

	public static FriendSearchRequest of(Long chosenUserId, List<Long> previousHops) {
		return FriendSearchRequest.builder()
			.chosenUserId(chosenUserId)
			.previousHops(previousHops)
			.build();
	}
}
