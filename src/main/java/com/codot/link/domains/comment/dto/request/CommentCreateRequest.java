package com.codot.link.domains.comment.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentCreateRequest {

	@NotNull(message = "댓글을 등록할 게시물의 id값은 필수입니다.")
	@Min(value = 1, message = "id값은 1 이상이어야 합니다.")
	private Long postId;

	@NotEmpty(message = "댓글 내용은 필수이며 공백 문자열을 허용되지 않습니다.")
	private String content;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private CommentCreateRequest(Long postId, String content) {
		this.postId = postId;
		this.content = content;
	}

	public static CommentCreateRequest of(Long postId, String content) {
		return CommentCreateRequest.builder()
			.postId(postId)
			.content(content)
			.build();
	}
}
