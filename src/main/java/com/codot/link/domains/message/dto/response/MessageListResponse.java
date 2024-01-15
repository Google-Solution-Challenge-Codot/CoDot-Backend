package com.codot.link.domains.message.dto.response;

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
public class MessageListResponse {

	private Integer messageCount;
	private List<MessageResponse> messages;

	@Builder(access = AccessLevel.PRIVATE)
	private MessageListResponse(List<MessageResponse> messages) {
		this.messageCount = messages.size();
		this.messages = messages;
	}

	public static MessageListResponse from(List<MessageResponse> messages) {
		return MessageListResponse.builder()
			.messages(messages)
			.build();
	}
}
