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
public class SubCommentCreateRequest {

	@NotNull(message = "대댓글을 등록할 게시물의 id값은 필수입니다.")
	@Min(value = 1, message = "id값은 1 이상이어야 합니다.")
	private Long postId;

	@NotNull(message = "대댓글을 등록할 댓글의 id값은 필수입니다.")
	@Min(value = 1, message = "id값은 1 이상이어야 합니다.")
	private Long commentId;

	@NotEmpty(message = "대댓글 내용은 필수이며 공백 문자열을 허용되지 않습니다.")
	private String content;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private SubCommentCreateRequest(Long postId, Long commentId, String content) {
		this.postId = postId;
		this.commentId = commentId;
		this.content = content;
	}

	public static SubCommentCreateRequest of(Long postId, Long commentId, String content) {
		return SubCommentCreateRequest.builder()
			.postId(postId)
			.commentId(commentId)
			.content(content)
			.build();
	}
}
