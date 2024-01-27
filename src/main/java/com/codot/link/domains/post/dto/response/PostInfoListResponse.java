package com.codot.link.domains.post.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostInfoListResponse {

	private Integer postCount;
	private List<PostResponse> posts;

	@Builder(access = AccessLevel.PRIVATE)
	private PostInfoListResponse(List<PostResponse> posts) {
		this.postCount = posts.size();
		this.posts = posts;
	}

	public static PostInfoListResponse from(List<PostResponse> posts) {
		return PostInfoListResponse.builder()
			.posts(posts)
			.build();
	}
}
