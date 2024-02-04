package com.codot.link.domains.fcm.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationRequest {

	private String title;
	private String content;

	@Builder(access = AccessLevel.PRIVATE)
	private NotificationRequest(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public static NotificationRequest of(String title, String content) {
		return NotificationRequest.builder()
			.title(title)
			.content(content)
			.build();
	}
}
