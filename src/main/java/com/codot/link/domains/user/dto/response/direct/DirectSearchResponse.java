package com.codot.link.domains.user.dto.response.direct;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DirectSearchResponse {

	private FirstHopResponse firstHop;
	private SecondHopResponse secondHop;
	private ThirdHopResponse thirdHop;

	// 매번 들어오는 매개변수가 다르므로 이 경우엔 특별하게 builder 그 자체로 사용. 정적 생성 메서드 사용하지 않음.
	@Builder
	public DirectSearchResponse(FirstHopResponse firstHop, SecondHopResponse secondHop, ThirdHopResponse thirdHop) {
		this.firstHop = firstHop;
		this.secondHop = secondHop;
		this.thirdHop = thirdHop;
	}
}
