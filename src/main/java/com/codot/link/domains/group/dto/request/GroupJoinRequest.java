package com.codot.link.domains.group.dto.request;

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
public class GroupJoinRequest {

	@Min(value = 1, message = "id값은 1 이상이어야 합니다.")
	@NotNull(message = "가입 요청을 보내려는 그룹 ID는 필수입니다.")
	private Long groupId;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private GroupJoinRequest(Long groupId) {
		this.groupId = groupId;
	}

	public static GroupJoinRequest from(Long groupId) {
		return GroupJoinRequest.builder()
			.groupId(groupId)
			.build();
	}
}
