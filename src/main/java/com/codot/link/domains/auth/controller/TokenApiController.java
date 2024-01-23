package com.codot.link.domains.auth.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.auth.dto.request.IssueTokenRequest;
import com.codot.link.domains.auth.dto.response.IssueTokenResponse;
import com.codot.link.domains.auth.service.TokenService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TokenApiController {

	private final TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<IssueTokenResponse> issueJwt(@Valid @RequestBody IssueTokenRequest request) {
		IssueTokenResponse response = tokenService.issueJwt(request);
		return ResponseEntity
			.status(CREATED)
			.body(response);
	}

	@GetMapping("/reissue")
	public ResponseEntity<Void> reissueJwt(@RequestHeader("user-id") Long userId,
		@RequestHeader("Authorization") String refreshToken, HttpServletResponse response) {
		String token = tokenService.reissueJwt(userId, refreshToken);
		response.setHeader("access-token", token);
		return ResponseEntity
			.status(OK)
			.build();
	}
}
