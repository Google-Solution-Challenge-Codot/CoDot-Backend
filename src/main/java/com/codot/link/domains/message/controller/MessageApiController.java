package com.codot.link.domains.message.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.message.dto.request.MessageCreateRequest;
import com.codot.link.domains.message.service.MessageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageApiController {

	private final MessageService messageService;

	@PostMapping
	public ResponseEntity<Void> sendMessage(@RequestHeader("user-id") Long userId,
		@Valid @RequestBody MessageCreateRequest request) {
		messageService.sendMessage(userId, request);
		return ResponseEntity
			.status(CREATED)
			.build();
	}
}
