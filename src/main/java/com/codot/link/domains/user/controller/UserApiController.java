package com.codot.link.domains.user.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.user.dto.request.FriendSearchRequest;
import com.codot.link.domains.user.dto.request.UserDeleteRequest;
import com.codot.link.domains.user.dto.request.UserSignupRequest;
import com.codot.link.domains.user.dto.request.UserUpdateRequest;
import com.codot.link.domains.user.dto.response.TwoHopListResponse;
import com.codot.link.domains.user.dto.response.UserInfoResponse;
import com.codot.link.domains.user.dto.response.direct.DirectSearchResponse;
import com.codot.link.domains.user.dto.response.friend.FriendSearchListResponse;
import com.codot.link.domains.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<Void> userSignup(@Valid @RequestBody UserSignupRequest request,
		@RequestHeader("login-record-id") Long loginRecordId, HttpServletResponse response) {
		String token = userService.userSignup(loginRecordId, request);
		response.setHeader("access-token", token);
		return ResponseEntity
			.status(CREATED)
			.build();
	}

	@GetMapping
	public ResponseEntity<UserInfoResponse> userInfo(@RequestHeader("user-id") Long userId) {
		UserInfoResponse response = userService.userInfo(userId);
		return ResponseEntity
			.status(OK)
			.body(response);
	}

	@PatchMapping
	public ResponseEntity<Void> userUpdate(@Valid @RequestBody UserUpdateRequest request,
		@RequestHeader("user-id") Long userId, HttpServletResponse response) {
		String token = userService.userUpdate(userId, request);
		response.setHeader("access-token", token);
		return ResponseEntity
			.noContent()
			.build();
	}

	@DeleteMapping
	public ResponseEntity<Void> userDelete(@Valid @RequestBody UserDeleteRequest request,
		@RequestHeader("user-id") Long userId) {
		userService.userDelete(userId, request);
		return ResponseEntity
			.noContent()
			.build();
	}

	@GetMapping("/home")
	public ResponseEntity<TwoHopListResponse> userHome(@RequestHeader("user-id") Long userId) {
		TwoHopListResponse response = userService.userHome(userId);
		return ResponseEntity
			.status(OK)
			.body(response);
	}

	@GetMapping("/direct_search")
	public ResponseEntity<DirectSearchResponse> userDirectSearch(@RequestHeader("user-id") Long userId,
		@RequestParam("nickname") String nickname) {
		DirectSearchResponse response = userService.userDirectSearch(userId, nickname);
		return ResponseEntity
			.status(OK)
			.body(response);
	}

	@GetMapping("/friend_search")
	public ResponseEntity<FriendSearchListResponse> userDirectSearch(@RequestHeader("user-id") Long userId,
		@Valid @RequestBody FriendSearchRequest request) {
		FriendSearchListResponse response = userService.userFriendSearch(request);
		return ResponseEntity
			.status(OK)
			.body(response);
	}
}
