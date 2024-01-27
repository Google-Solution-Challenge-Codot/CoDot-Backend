package com.codot.link.domains.post.dto.response;

import com.codot.link.domains.post.domain.Post;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostResponse {

	private Long postId;
	private String title;

	@Builder(access = AccessLevel.PRIVATE)
	protected PostResponse(Long postId, String title) {
		this.postId = postId;
		this.title = title;
	}

	public static PostResponse from(Post post) {
		return PostResponse.builder()
			.postId(post.getId())
			.title(post.getTitle())
			.build();
	}
}
