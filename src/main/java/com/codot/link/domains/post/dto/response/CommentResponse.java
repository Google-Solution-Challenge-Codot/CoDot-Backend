package com.codot.link.domains.post.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.codot.link.domains.comment.domain.Comment;
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
public class CommentResponse {

	private Long commentId;
	private LocalDateTime createdAt;
	private String content;
	private Long writerId;
	private String writerNickname;
	private String writerFilePath;
	private Integer subCommentCount;
	private List<SubCommentResponse> subComments;

	@Builder(access = AccessLevel.PRIVATE)
	private CommentResponse(Long commentId, LocalDateTime createdAt, String content, Long writerId,
		String writerNickname, String writerFilePath,
		List<SubCommentResponse> subComments) {
		this.commentId = commentId;
		this.createdAt = createdAt;
		this.content = content;
		this.writerId = writerId;
		this.writerNickname = writerNickname;
		this.writerFilePath = writerFilePath;
		this.subCommentCount = subComments.size();
		this.subComments = subComments;
	}

	public static CommentResponse of(Comment comment, List<SubCommentResponse> subComments) {
		User user = comment.getUser();
		return CommentResponse.builder()
			.commentId(comment.getId())
			.createdAt(comment.getCreatedAt())
			.content(comment.getContent())
			.writerId(user.getId())
			.writerNickname(user.getNickname())
			.writerFilePath(user.getFilePath())
			.subComments(subComments)
			.build();
	}
}
