package com.codot.link.domains.post.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostModifyRequest {

	private String title;
	private String content;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private PostModifyRequest(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public static PostModifyRequest of(String title, String content) {
		return PostModifyRequest.builder()
			.title(title)
			.content(content)
			.build();
	}
}
