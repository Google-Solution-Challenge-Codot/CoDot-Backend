package com.codot.link.domains.post.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
