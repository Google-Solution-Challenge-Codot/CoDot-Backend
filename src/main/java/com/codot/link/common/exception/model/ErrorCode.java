package com.codot.link.common.exception.model;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// 409 CONFLICT
	DUPLICATE_USER_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다.");

	private final String message;
	private final int statusCode;

	ErrorCode(HttpStatus status, String message) {
		this.statusCode = status.value();
		this.message = message;
	}
}
