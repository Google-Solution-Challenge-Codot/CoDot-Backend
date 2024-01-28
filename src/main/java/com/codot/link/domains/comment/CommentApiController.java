package com.codot.link.domains.comment;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.comment.dto.request.CommentCreateRequest;
import com.codot.link.domains.comment.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentApiController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> createComment(@RequestHeader("user-id") Long userId,
		@Valid @RequestBody CommentCreateRequest request) {
		commentService.createComment(userId, request);
		return ResponseEntity
			.status(CREATED)
			.build();
	}
}
