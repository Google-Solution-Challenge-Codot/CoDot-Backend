package com.codot.link.common.exception.model;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final String detail;
	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode, String detail) {
		this.errorCode = errorCode;
		this.detail = detail;
	}

	public CustomException(ErrorCode errorCode) {
		this(errorCode, "");
	}
}
