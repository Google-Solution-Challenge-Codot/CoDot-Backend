package com.codot.link.domains.comment.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.comment.dto.request.SubCommentCreateRequest;
import com.codot.link.domains.comment.dto.request.SubCommentModifyRequest;
import com.codot.link.domains.comment.service.SubCommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sub_comments")
public class SubCommentApiController {

	private final SubCommentService subCommentService;

	@PostMapping
	public ResponseEntity<Void> createSubComment(@RequestHeader("user-id") Long userId,
		@Valid @RequestBody SubCommentCreateRequest request) {
		subCommentService.createSubComment(userId, request);
		return ResponseEntity
			.status(CREATED)
			.build();
	}

	@PatchMapping("/{sub_comment_id}")
	public ResponseEntity<Void> modifySubComment(@RequestHeader("user-id") Long userId,
		@PathVariable("sub_comment_id") Long subCommentId, @Valid @RequestBody SubCommentModifyRequest request) {
		subCommentService.modifySubComment(userId, subCommentId, request);
		return ResponseEntity
			.noContent()
			.build();
	}

	@DeleteMapping("/{sub_comment_id}")
	public ResponseEntity<Void> deleteSubComment(@RequestHeader("user-id") Long userId,
		@PathVariable("sub_comment_id") Long subCommentId) {
		subCommentService.deleteSubComment(userId, subCommentId);
		return ResponseEntity
			.noContent()
			.build();
	}
}
