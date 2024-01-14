package com.codot.link.common.exception.model;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	//400 BAD_REQUEST
	EMAIL_NOT_MATCH(HttpStatus.BAD_REQUEST, "이메일이 일치하지 않습니다."),

	// 404 NOT_FOUND
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),

	// 409 CONFLICT
	DUPLICATE_USER_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
	EXISTING_FRIEND_REQUEST(HttpStatus.CONFLICT, "친구 추가 요청이 이미 존재합니다.");

	private final String message;
	private final int statusCode;

	ErrorCode(HttpStatus status, String message) {
		this.statusCode = status.value();
		this.message = message;
	}
}
