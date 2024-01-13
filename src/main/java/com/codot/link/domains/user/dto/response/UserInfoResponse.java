package com.codot.link.domains.user.dto.response;

import com.codot.link.domains.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponse {

	private String nickname;
	private String university;
	private String department;
	private String introduction;

	@Builder(access = AccessLevel.PRIVATE)
	private UserInfoResponse(String nickname, String university, String department, String introduction) {
		this.nickname = nickname;
		this.university = university;
		this.department = department;
		this.introduction = introduction;
	}

	public static UserInfoResponse from(User user) {
		return UserInfoResponse.builder()
			.nickname(user.getNickname())
			.university(user.getUniversity())
			.department(user.getDepartment())
			.introduction(user.getIntroduction())
			.build();
	}
}
