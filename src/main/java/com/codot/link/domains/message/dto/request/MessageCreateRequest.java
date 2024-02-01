package com.codot.link.domains.message.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MessageCreateRequest {

	@NotNull(message = "메세지 수신 대상자 id값은 필수입니다.")
	@Min(value = 1, message = "id값은 1 이상이어야 합니다.")
	private Long targetId;

	@NotBlank(message = "제목은 필수이며 공백 문자열은 허용되지 않습니다.")
	private String title;

	@NotBlank(message = "내용은 필수이며 공백 문자열은 허용되지 않습니다.")
	private String content;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private MessageCreateRequest(Long targetId, String title, String content) {
		this.targetId = targetId;
		this.title = title;
		this.content = content;
	}

	public static MessageCreateRequest of(Long targetId, String title, String content) {
		return MessageCreateRequest.builder()
			.targetId(targetId)
			.title(title)
			.content(content)
			.build();
	}
}
