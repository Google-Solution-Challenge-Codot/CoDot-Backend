package com.codot.link.domains.post.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostCreateRequest {

	@NotNull(message = "게시물을 등록할 그룹의 id값은 필수입니다.")
	@Min(value = 1, message = "id값은 1 이상이어야 합니다.")
	private Long groupId;

	@NotBlank(message = "제목은 필수이며 공백 문자열은 허용되지 않습니다.")
	private String title;

	@NotBlank(message = "내용은 필수이며 공백 문자열은 허용되지 않습니다.")
	private String content;

	// === Test 용도 === //
	@Builder(access = AccessLevel.PRIVATE)
	private PostCreateRequest(Long groupId, String title, String content) {
		this.groupId = groupId;
		this.title = title;
		this.content = content;
	}

	public static PostCreateRequest of(Long groupId, String title, String content) {
		return PostCreateRequest.builder()
			.groupId(groupId)
			.title(title)
			.content(content)
			.build();
	}
}
