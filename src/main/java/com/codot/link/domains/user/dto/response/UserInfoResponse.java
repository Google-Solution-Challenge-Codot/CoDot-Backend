package com.codot.link.domains.user.dto.response;

import com.codot.link.domains.user.domain.GraduationStatus;
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
public class UserInfoResponse {

	private String filePath;
	private String nickname;
	private String university;
	private String department;
	private GraduationStatus graduationStatus;
	private String introduction;

	@Builder(access = AccessLevel.PRIVATE)
	private UserInfoResponse(String filePath, String nickname, String university, String department,
		GraduationStatus graduationStatus, String introduction) {
		this.filePath = filePath;
		this.nickname = nickname;
		this.university = university;
		this.department = department;
		this.graduationStatus = graduationStatus;
		this.introduction = introduction;
	}

	public static UserInfoResponse from(User user) {
		return UserInfoResponse.builder()
			.filePath(user.getFilePath())
			.nickname(user.getNickname())
			.university(user.getUniversity())
			.department(user.getDepartment())
			.graduationStatus(user.getGraduationStatus())
			.introduction(user.getIntroduction())
			.build();
	}
}
