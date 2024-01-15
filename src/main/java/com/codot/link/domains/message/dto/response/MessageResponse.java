package com.codot.link.domains.message.dto.response;

import com.codot.link.domains.message.domain.Message;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MessageResponse {

	private Long messageId;
	private Long senderId;
	private String title;
	private String content;

	@Builder(access = AccessLevel.PRIVATE)
	private MessageResponse(Long messageId, Long senderId, String title, String content) {
		this.messageId = messageId;
		this.senderId = senderId;
		this.title = title;
		this.content = content;
	}

	public static MessageResponse from(Message message) {
		return MessageResponse.builder()
			.messageId(message.getId())
			.senderId(message.getSender().getId())
			.title(message.getTitle())
			.content(message.getContent())
			.build();
	}
}
