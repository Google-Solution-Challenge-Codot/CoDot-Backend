package com.codot.link.domains.user.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TwoHopListResponse {

	private Integer twoHopCount;
	private List<TwoHopResponse> twoHops;

	@Builder(access = AccessLevel.PRIVATE)
	private TwoHopListResponse(List<TwoHopResponse> twoHops) {
		this.twoHopCount = twoHops.size();
		this.twoHops = twoHops;
	}

	public static TwoHopListResponse from(List<TwoHopResponse> twoHops) {
		return TwoHopListResponse.builder()
			.twoHops(twoHops)
			.build();
	}
}
