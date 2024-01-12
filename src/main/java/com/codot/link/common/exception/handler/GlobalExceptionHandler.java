package com.codot.link.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codot.link.common.exception.dto.response.CustomExceptionResponse;
import com.codot.link.common.exception.model.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<CustomExceptionResponse> customExceptionResponse(CustomException exception) {
		return ResponseEntity
			.status(exception.getErrorCode().getStatusCode())
			.body(CustomExceptionResponse.from(exception));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> customExceptionResponse(RuntimeException exception) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(exception.getMessage());
	}
}
