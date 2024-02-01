package com.codot.link.domains.group.service;

import static com.codot.link.common.exception.model.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.group.domain.GroupUser;
import com.codot.link.domains.group.domain.Role;
import com.codot.link.domains.group.dto.request.GroupRequestInfoRequest;
import com.codot.link.domains.group.dto.response.GroupRequestInfoListResponse;
import com.codot.link.domains.group.dto.response.GroupRequestInfoResponse;
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

	public void declineGroupJoinRequest(Long groupUserId) {
		groupUserRepository.deleteById(groupUserId);
	}

	public GroupRequestInfoListResponse groupRequestInfo(Long userId, GroupRequestInfoRequest request) {
		checkUserRoleWithGroup(userId, request.getGroupId());
		
		List<GroupRequestInfoResponse> groupRequests = groupUserRepository.findAllByGroup_IdAndAccepted(
				request.getGroupId(), false).stream()
			.map(GroupRequestInfoResponse::from)
			.toList();
		return GroupRequestInfoListResponse.from(groupRequests);
	}

	private void checkUserRoleWithGroup(Long userId, Long groupId) {
		if (!groupUserRepository.existsByUser_IdAndGroup_IdAndRole(userId, groupId, Role.HOST)) {
			throw CustomException.from(NOT_HOST_OF_THE_GROUP);
		}
	}
}
