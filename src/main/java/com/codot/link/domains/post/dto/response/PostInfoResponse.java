package com.codot.link.domains.post.dto.response;

import com.codot.link.domains.post.domain.Post;
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
public class PostInfoResponse extends PostResponse {

	private String content;
	private Long postWriterId;
	private String postWriterFilePath;
	private String postWriterNickname;
	// TODO: Comment 도메인 개발 완료 후 해당 게시물 Comment 정보도 포함하기

	@Builder(access = AccessLevel.PRIVATE)
	private PostInfoResponse(Long postId, String title, String content, Long postWriterId, String postWriterFilePath,
		String postWriterNickname) {
		super(postId, title);
		this.content = content;
		this.postWriterId = postWriterId;
		this.postWriterFilePath = postWriterFilePath;
		this.postWriterNickname = postWriterNickname;
	}

	public static PostInfoResponse from(Post post) {
		User user = post.getUser();
		return PostInfoResponse.builder()
			.postId(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.postWriterId(user.getId())
			.postWriterFilePath(user.getFilePath())
			.postWriterNickname(user.getNickname())
			.build();
	}
}
