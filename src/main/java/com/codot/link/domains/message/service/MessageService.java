package com.codot.link.domains.message.service;

import static com.codot.link.common.exception.model.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.message.domain.Message;
import com.codot.link.domains.message.dto.request.MessageCreateRequest;
import com.codot.link.domains.message.repository.MessageRepository;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
	private final UserRepository userRepository;

	public void sendMessage(Long userId, MessageCreateRequest request) {
		User sender = findUserByUserId(userId);
		User receiver = findUserByUserId(request.getTargetId());

		messageRepository.save(Message.of(sender, receiver, request));
	}

	private User findUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
	}
}
