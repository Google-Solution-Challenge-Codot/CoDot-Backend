package com.codot.link.domains.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentModifyRequest {

	@NotBlank(message = "수정할 댓글 내용은 필수이며 공백 문자열을 허용되지 않습니다.")
	private String content;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private CommentModifyRequest(String content) {
		this.content = content;
	}

	public static CommentModifyRequest from(String content) {
		return CommentModifyRequest.builder()
			.content(content)
			.build();
	}
}
