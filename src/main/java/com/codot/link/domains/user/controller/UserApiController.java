package com.codot.link.domains.user.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.user.dto.request.UserSignupRequest;
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
}
