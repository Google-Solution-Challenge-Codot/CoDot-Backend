package com.codot.link.domains.post.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.post.dto.request.PostCreateRequest;
import com.codot.link.domains.post.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<Void> createPost(@RequestHeader("user-id") Long userId,
		@Valid @RequestBody PostCreateRequest request) {
		postService.createPost(userId, request);
		return ResponseEntity
			.status(CREATED)
			.build();
	}
}
