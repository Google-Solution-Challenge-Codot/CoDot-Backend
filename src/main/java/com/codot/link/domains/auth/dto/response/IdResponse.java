package com.codot.link.domains.auth.dto.response;

import com.codot.link.domains.auth.domain.IdType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class IdResponse {

	private IdType idType;
	private Long id;

	@Builder(access = AccessLevel.PRIVATE)
	private IdResponse(IdType idType, Long id) {
		this.idType = idType;
		this.id = id;
	}

	public static IdResponse of(IdType idType, Long id) {
		return IdResponse.builder()
			.idType(idType)
			.id(id)
			.build();
	}
}