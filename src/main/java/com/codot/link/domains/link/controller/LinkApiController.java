package com.codot.link.domains.link.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.link.dto.request.FriendRequest;
import com.codot.link.domains.link.dto.response.FriendRequestListResponse;
import com.codot.link.domains.link.service.LinkService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/links")
public class LinkApiController {

	private final LinkService linkService;

	@PostMapping("/request")
	public ResponseEntity<Void> friendRequest(@Valid @RequestBody FriendRequest request,
		@RequestHeader("user-id") Long userId) {
		linkService.createFriendRequest(userId, request);
		return ResponseEntity
			.status(CREATED)
			.build();
	}

	@GetMapping("/request")
	public ResponseEntity<FriendRequestListResponse> friendRequestInfo(@RequestHeader("user-id") Long userId) {
		FriendRequestListResponse response = linkService.friendRequestList(userId);
		return ResponseEntity
			.status(OK)
			.body(response);
	}
}
