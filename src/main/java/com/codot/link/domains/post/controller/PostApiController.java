package com.codot.link.domains.post.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.post.dto.request.PostCreateRequest;
import com.codot.link.domains.post.dto.request.PostModifyRequest;
import com.codot.link.domains.post.dto.response.PostInfoListResponse;
import com.codot.link.domains.post.dto.response.PostInfoResponse;
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

	@GetMapping("/{post_id}")
	public ResponseEntity<PostInfoResponse> postInfo(@RequestHeader("user-id") Long userId,
		@PathVariable("post_id") Long postId) {
		PostInfoResponse response = postService.postInfo(userId, postId);
		return ResponseEntity
			.status(OK)
			.body(response);
	}

	@GetMapping("/group/{group_id}")
	public ResponseEntity<PostInfoListResponse> postList(@RequestHeader("user-id") Long userId,
		@PathVariable("group_id") Long groupId) {
		PostInfoListResponse response = postService.postList(userId, groupId);
		return ResponseEntity
			.status(OK)
			.body(response);
	}

	@PatchMapping("/{post_id}")
	public ResponseEntity<Void> modifyPost(@RequestHeader("user-id") Long userId, @PathVariable("post_id") Long postId,
		@RequestBody PostModifyRequest request) {
		postService.modifyPost(userId, postId, request);
		return ResponseEntity
			.noContent()
			.build();
	}

	@DeleteMapping("/{post_id}")
	public ResponseEntity<Void> deletePost(@RequestHeader("user-id") Long userId,
		@PathVariable("post_id") Long postId) {
		postService.deletePost(userId, postId);
		return ResponseEntity
			.noContent()
			.build();
	}
}
