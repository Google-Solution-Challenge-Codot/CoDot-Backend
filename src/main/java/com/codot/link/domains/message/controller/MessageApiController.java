package com.codot.link.domains.message.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codot.link.domains.message.dto.request.MessageCreateRequest;
import com.codot.link.domains.message.dto.response.MessageListResponse;
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

	@GetMapping
	public ResponseEntity<MessageListResponse> messageInfo(@RequestHeader("user-id") Long userId) {
		MessageListResponse response = messageService.receivedMessageList(userId);
		return ResponseEntity
			.status(OK)
			.body(response);
	}

	@DeleteMapping("/{message_id}")
	public ResponseEntity<Void> deleteMessage(@PathVariable("message_id") Long messageId) {
		messageService.deleteMessage(messageId);
		return ResponseEntity
			.noContent()
			.build();
	}
}
