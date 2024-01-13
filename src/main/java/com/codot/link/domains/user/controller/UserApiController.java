package com.codot.link.domains.user.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.user.dto.request.UserSignupRequest;
import com.codot.link.domains.user.dto.response.UserInfoResponse;
import com.codot.link.domains.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<Void> userSignup(@Valid @RequestBody UserSignupRequest request) {
		userService.userSignup(request);
		return ResponseEntity
			.status(CREATED)
			.build();
	}

	@GetMapping
	public ResponseEntity<UserInfoResponse> userInfo() {
		// TODO: user_id 하드코딩 제거하고 header에서 값 추출하여 사용하기
		Long userId = 1L;
		
		UserInfoResponse response = userService.userInfo(userId);
		return ResponseEntity
			.status(OK)
			.body(response);
	}
}
