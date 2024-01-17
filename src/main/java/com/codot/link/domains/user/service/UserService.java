package com.codot.link.domains.user.service;

import static com.codot.link.common.exception.model.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.link.domain.Link;
import com.codot.link.domains.link.repository.LinkRepository;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.dto.request.UserDeleteRequest;
import com.codot.link.domains.user.dto.request.UserSignupRequest;
import com.codot.link.domains.user.dto.request.UserUpdateRequest;
import com.codot.link.domains.user.dto.response.TwoHopListResponse;
import com.codot.link.domains.user.dto.response.TwoHopResponse;
import com.codot.link.domains.user.dto.response.UserInfoResponse;
import com.codot.link.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final LinkRepository linkRepository;

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

	public void userDelete(Long userId, UserDeleteRequest request) {
		User user = findOne(userId);
		confirmUserByEmail(user, request.getEmail());
		userRepository.delete(user);
	}

	public User findOne(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
	}

	private void confirmUserByEmail(User user, String email) {
		if (!user.getEmail().equals(email)) {
			throw CustomException.from(EMAIL_NOT_MATCH);
		}
	}

	private void validateNicknameDuplicate(String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			throw CustomException.from(DUPLICATE_USER_NICKNAME);
		}
	}

	public TwoHopListResponse userHome(Long userId) {
		List<Link> links = linkRepository.findTwoHops(userId);

		List<TwoHopResponse> twoHops = links.stream()
			.map(TwoHopResponse::from)
			.toList();
		return TwoHopListResponse.from(twoHops);
	}
}
