package com.codot.link.common.exception.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final String detail;
	private final ErrorCode errorCode;

	@Builder(access = AccessLevel.PRIVATE)
	private CustomException(ErrorCode errorCode, String detail) {
		this.errorCode = errorCode;
		this.detail = detail;
	}

	public static CustomException from(ErrorCode errorCode) {
		return CustomException.builder()
			.errorCode(errorCode)
			.detail("")
			.build();
	}

	public static CustomException of(ErrorCode errorCode, String detail) {
		return CustomException.builder()
			.errorCode(errorCode)
			.detail(detail)
			.build();
	}
}
