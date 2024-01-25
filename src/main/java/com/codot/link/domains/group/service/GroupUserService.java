package com.codot.link.domains.group.service;

import static com.codot.link.common.exception.model.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.group.domain.GroupUser;
import com.codot.link.domains.group.repository.GroupUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupUserService {

	private final GroupUserRepository groupUserRepository;

	public void acceptGroupJoinRequest(Long groupUserId) {
		GroupUser groupUser = findOne(groupUserId);

		checkGroupUserStatus(groupUser);
		groupUser.joinGroup();
	}

	private void checkGroupUserStatus(GroupUser groupUser) {
		if (groupUser.isAccepted()) {
			throw CustomException.from(ALREADY_ACCEPTED_GROUP_JOIN_REQUEST);
		}
	}

	private GroupUser findOne(Long groupUserId) {
		return groupUserRepository.findById(groupUserId)
			.orElseThrow(() -> CustomException.from(GROUP_USER_NOT_FOUND));
	}
}
