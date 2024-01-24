package com.codot.link.domains.group.service;

import static com.codot.link.common.exception.model.ErrorCode.*;
import static com.codot.link.domains.group.domain.Role.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.group.domain.Group;
import com.codot.link.domains.group.domain.GroupUser;
import com.codot.link.domains.group.dto.request.GroupCreateRequest;
import com.codot.link.domains.group.dto.response.GroupInfoResponse;
import com.codot.link.domains.group.repository.GroupRepository;
import com.codot.link.domains.group.repository.GroupUserRepository;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {

	private final UserRepository userRepository;
	private final GroupRepository groupRepository;
	private final GroupUserRepository groupUserRepository;

	public void createGroup(Long userId, GroupCreateRequest request) {
		validateGroupNameDuplicate(request);

		Group group = groupRepository.save(Group.from(request));
		User user = findUserByUserId(userId);

		groupUserRepository.save(GroupUser.of(user, group, HOST, true));
	}

	private void validateGroupNameDuplicate(GroupCreateRequest request) {
		if (groupRepository.existsByName(request.getName())) {
			throw CustomException.from(DUPLICATE_GROUP_NAME);
		}
	}

	private User findUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
	}

	public GroupInfoResponse groupInfo(Long groupId) {
		// TODO: 나중에 해당 그룹의 게시물 정보도 포함할 것
		return GroupInfoResponse.from(findOne(groupId));
	}

	private Group findOne(Long groupId) {
		return groupRepository.findById(groupId)
			.orElseThrow(() -> CustomException.from(GROUP_NOT_FOUND));
	}
}
