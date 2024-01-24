package com.codot.link.common.exception.model;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	//400 BAD_REQUEST
	EMAIL_NOT_MATCH(BAD_REQUEST, "이메일이 일치하지 않습니다."),
	ID_NOT_PRESENT(BAD_REQUEST, "헤더에 어떠한 id도 존재하지 않습니다."),

	//401 UNAUTHORIZED
	TOKEN_NOT_VALID(UNAUTHORIZED, "토큰이 유효하지 않습니다."),

	// 404 NOT_FOUND
	USER_NOT_FOUND(NOT_FOUND, "존재하지 않는 사용자입니다."),
	LINK_NOT_FOUND(NOT_FOUND, "존재하지 않는 LINK입니다."),
	LOGIN_RECORD_NOT_FOUND(NOT_FOUND, "존재하지 않는 Login Record입니다."),
	GROUP_NOT_FOUND(NOT_FOUND, "존재하지 않는 그룹입니다."),

	// 409 CONFLICT
	DUPLICATE_USER_NICKNAME(CONFLICT, "이미 존재하는 닉네임입니다."),
	DUPLICATE_GROUP_NAME(CONFLICT, "이미 존재하는 그룹 이름입니다."),
	ALREADY_ACCEPTED_FRIEND_REQUEST(CONFLICT, "이미 수락된 친구 추가 요청입니다."),
	EXISTING_FRIEND_REQUEST(CONFLICT, "친구 추가 요청이 이미 존재합니다."),
	REFRESHTOKEN_NOT_MATCH(CONFLICT, "Refresh Token이 일치하지 않습니다.");

	private final String message;
	private final int statusCode;

	ErrorCode(HttpStatus status, String message) {
		this.statusCode = status.value();
		this.message = message;
	}
}
