package com.codot.link.domains.group.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.group.dto.request.GroupCreateRequest;
import com.codot.link.domains.group.dto.request.GroupModifyRequest;
import com.codot.link.domains.group.dto.response.GroupInfoResponse;
import com.codot.link.domains.group.service.GroupService;
import com.codot.link.domains.group.service.GroupUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupApiController {

	private final GroupService groupService;
	private final GroupUserService groupUserService;

	@PostMapping
	public ResponseEntity<Void> createGroup(@Valid @RequestBody GroupCreateRequest request,
		@RequestHeader("user-id") Long userId) {
		groupService.createGroup(userId, request);
		return ResponseEntity
			.status(CREATED)
			.build();
	}

	@GetMapping("/{group_id}")
	public ResponseEntity<GroupInfoResponse> groupInfo(@PathVariable("group_id") Long groupId) {
		GroupInfoResponse response = groupService.groupInfo(groupId);
		return ResponseEntity
			.status(OK)
			.body(response);
	}

	@PatchMapping("/{group_id}")
	public ResponseEntity<Void> modifyGroup(@RequestHeader("user-id") Long userId,
		@PathVariable("group_id") Long groupId, @RequestBody GroupModifyRequest request) {
		groupService.modifyGroup(userId, groupId, request);
		return ResponseEntity
			.noContent()
			.build();
	}
}
