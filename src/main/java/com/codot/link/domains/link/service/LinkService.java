package com.codot.link.domains.link.service;

import static com.codot.link.common.exception.model.ErrorCode.*;
import static com.codot.link.domains.link.domain.Status.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.link.domain.Link;
import com.codot.link.domains.link.dto.request.FriendRequest;
import com.codot.link.domains.link.dto.request.LinkUpdateRequest;
import com.codot.link.domains.link.dto.response.FriendRequestListResponse;
import com.codot.link.domains.link.dto.response.FriendRequestResponse;
import com.codot.link.domains.link.repository.LinkRepository;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkService {

	private final LinkRepository linkRepository;
	private final UserRepository userRepository;

	public void createFriendRequest(Long userId, FriendRequest request) {
		User source = findUserByUserId(userId);
		User target = findUserByUserId(request.getTargetId());

		checkExistingRequestForDuplicate(source, target);
		linkRepository.save(Link.of(source, target, PROCESSING));
	}

	private User findUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
	}

	private void checkExistingRequestForDuplicate(User source, User target) {
		// 이전에 해당 사용자에게 친추 요청을 보낸 적이 있는지 확인
		if (linkRepository.existsByFromAndTo(source, target)) {
			throw CustomException.of(EXISTING_FRIEND_REQUEST, "이미 친구 추가 요청을 보낸 적이 있습니다.");
		}

		// 해당 사용자가 나에게 친추 요청을 보낸 적이 있는지 확인
		if (linkRepository.existsByFromAndTo(target, source)) {
			throw CustomException.of(EXISTING_FRIEND_REQUEST, "해당 사용자가 나에게 보낸 친구 추가 요청이 있습니다.");
		}
	}

	public FriendRequestListResponse friendRequestList(Long userId) {
		List<Link> links = linkRepository.findAllByTo_IdAndStatus(userId, PROCESSING);

		List<FriendRequestResponse> friendRequests = links.stream()
			.map(FriendRequestResponse::from)
			.toList();

		return FriendRequestListResponse.from(friendRequests);
	}

	public void acceptFriendRequest(Long linkId) {
		Link friendRequest = findOne(linkId);
		checkLinkStatus(friendRequest);

		friendRequest.updateStatus(CONNECTED);
		linkRepository.save(Link.of(friendRequest.getTo(), friendRequest.getFrom(), CONNECTED));
	}

	private void checkLinkStatus(Link friendRequest) {
		if (!friendRequest.getStatus().equals(PROCESSING)) {
			throw CustomException.from(ALREADY_ACCEPTED_FRIEND_REQUEST);
		}
	}

	public void declineFriendRequest(Long linkId) {
		linkRepository.deleteById(linkId);
	}

	public void updateLinkConnectionPoint(Long linkId, LinkUpdateRequest request) {
		Link link = findOne(linkId);
		link.updateConnectionPoint(request.getConnectionPoint());
	}

	public void deleteLink(Long linkId) {
		Link link = findOne(linkId);
		Link reverseLink = findReverseLink(link);
		linkRepository.deleteAll(List.of(link, reverseLink));
	}

	private Link findOne(Long linkId) {
		return linkRepository.findById(linkId)
			.orElseThrow(() -> CustomException.from(LINK_NOT_FOUND));
	}

	private Link findReverseLink(Link link) {
		return linkRepository.findByFromAndTo(link.getTo(), link.getFrom())
			.orElseThrow(() -> CustomException.from(LINK_NOT_FOUND));
	}
}
