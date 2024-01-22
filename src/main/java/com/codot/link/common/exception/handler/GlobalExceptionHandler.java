package com.codot.link.common.exception.handler;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codot.link.common.exception.dto.response.CustomExceptionResponse;
import com.codot.link.common.exception.model.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> methodArgumentNotValidResponse(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();

		StringBuilder sb = new StringBuilder();
		bindingResult.getFieldErrors().forEach(fieldError -> {
			sb.append("[")
				.append(fieldError.getField())
				.append("]")
				.append(": ")
				.append(fieldError.getDefaultMessage())
				.append("\n");
		});
		return ResponseEntity
			.status(BAD_REQUEST)
			.body(sb.toString());
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<CustomExceptionResponse> customExceptionResponse(CustomException exception) {
		return ResponseEntity
			.status(exception.getErrorCode().getStatusCode())
			.body(CustomExceptionResponse.from(exception));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> customExceptionResponse(RuntimeException exception) {
		return ResponseEntity
			.status(INTERNAL_SERVER_ERROR)
			.body(exception.getMessage());
	}
}
