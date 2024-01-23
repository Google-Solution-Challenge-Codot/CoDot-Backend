package com.codot.link.domains.auth.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.auth.dto.request.IssueTokenRequest;
import com.codot.link.domains.auth.dto.response.IssueTokenResponse;
import com.codot.link.domains.auth.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class TokenApiController {

	private final TokenService tokenService;

	@PostMapping
	public ResponseEntity<IssueTokenResponse> issueJwt(@Valid @RequestBody IssueTokenRequest request) {
		IssueTokenResponse response = tokenService.issueJwt(request);
		return ResponseEntity
			.status(CREATED)
			.body(response);
	}
}
