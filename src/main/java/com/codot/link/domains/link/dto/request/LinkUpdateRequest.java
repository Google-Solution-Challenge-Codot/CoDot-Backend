package com.codot.link.domains.link.dto.request;

import com.codot.link.domains.link.domain.ConnectionPoint;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LinkUpdateRequest {

	@NotNull(message = "연결 교점은 필수입니다.")
	private ConnectionPoint connectionPoint;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private LinkUpdateRequest(ConnectionPoint connectionPoint) {
		this.connectionPoint = connectionPoint;
	}

	public static LinkUpdateRequest from(ConnectionPoint connectionPoint) {
		return LinkUpdateRequest.builder()
			.connectionPoint(connectionPoint)
			.build();
	}
}
