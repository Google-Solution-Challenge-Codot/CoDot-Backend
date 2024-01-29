package com.codot.link.domains.post.dto.response;

import java.time.LocalDateTime;

import com.codot.link.domains.comment.domain.SubComment;
import com.codot.link.domains.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubCommentResponse {

	private Long subCommentId;
	private LocalDateTime createdAt;
	private String content;
	private Long writerId;
	private String writerNickname;
	private String writerFilePath;

	@Builder(access = AccessLevel.PRIVATE)
	private SubCommentResponse(Long subCommentId, LocalDateTime createdAt, String content, Long writerId,
		String writerNickname,
		String writerFilePath) {
		this.subCommentId = subCommentId;
		this.createdAt = createdAt;
		this.content = content;
		this.writerId = writerId;
		this.writerNickname = writerNickname;
		this.writerFilePath = writerFilePath;
	}

	public static SubCommentResponse from(SubComment subComment) {
		User user = subComment.getUser();
		return SubCommentResponse.builder()
			.subCommentId(subComment.getId())
			.createdAt(subComment.getCreatedAt())
			.content(subComment.getContent())
			.writerId(user.getId())
			.writerNickname(user.getNickname())
			.writerFilePath(user.getFilePath())
			.build();
	}
}
