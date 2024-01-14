package com.codot.link.domains.link.dto.response;

import com.codot.link.domains.link.domain.Link;
import com.codot.link.domains.link.domain.Status;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FriendRequestResponse {

	private Long linkId;
	private Long requestSenderId;
	private Status status;

	@Builder(access = AccessLevel.PRIVATE)
	private FriendRequestResponse(Long linkId, Long requestSenderId, Status status) {
		this.linkId = linkId;
		this.requestSenderId = requestSenderId;
		this.status = status;
	}

	public static FriendRequestResponse from(Link link) {
		return FriendRequestResponse.builder()
			.linkId(link.getId())
			.requestSenderId(link.getFrom().getId())
			.status(link.getStatus())
			.build();
	}
}
