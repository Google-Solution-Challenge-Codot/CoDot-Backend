package com.codot.link.domains.user.service;

import static com.codot.link.common.exception.model.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.dto.request.UserSignupRequest;
import com.codot.link.domains.user.dto.request.UserUpdateRequest;
import com.codot.link.domains.user.dto.response.UserInfoResponse;
import com.codot.link.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public void userSignup(UserSignupRequest request) {
		validateNicknameDuplicate(request.getNickname());
		userRepository.save(User.from(request));
	}

	public UserInfoResponse userInfo(Long userId) {
		User user = findOne(userId);
		return UserInfoResponse.from(user);
	}

	public void userUpdate(Long userId, UserUpdateRequest request) {
		User user = findOne(userId);

		if (request.getNickname() != null) {
			validateNicknameDuplicate(request.getNickname());
		}
		user.updateInfo(request);
	}

	public User findOne(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
	}

	private void validateNicknameDuplicate(String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			throw CustomException.from(DUPLICATE_USER_NICKNAME);
		}
	}
}
