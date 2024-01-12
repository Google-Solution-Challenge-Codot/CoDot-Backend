package com.codot.link.common.exception.dto.response;

import static java.time.LocalDateTime.*;

import java.time.LocalDateTime;

import com.codot.link.common.exception.model.CustomException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomExceptionResponse {

	private final LocalDateTime time;
	private final String message;
	private final String detail;

	@Builder(access = AccessLevel.PRIVATE)
	private CustomExceptionResponse(CustomException exception) {
		time = now();
		message = exception.getErrorCode().getMessage();
		detail = exception.getDetail();
	}

	public static CustomExceptionResponse from(CustomException exception) {
		return CustomExceptionResponse.builder()
			.exception(exception)
			.build();
	}
}
