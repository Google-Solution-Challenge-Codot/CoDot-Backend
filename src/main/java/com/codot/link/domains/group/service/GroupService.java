package com.codot.link.domains.group.service;

import static com.codot.link.common.exception.model.ErrorCode.*;
import static com.codot.link.domains.group.domain.Role.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.group.dao.MyGroupResult;
import com.codot.link.domains.group.domain.Group;
import com.codot.link.domains.group.domain.GroupUser;
import com.codot.link.domains.group.dto.request.GroupCreateRequest;
import com.codot.link.domains.group.dto.request.GroupDeleteRequest;
import com.codot.link.domains.group.dto.request.GroupJoinRequest;
import com.codot.link.domains.group.dto.request.GroupModifyRequest;
import com.codot.link.domains.group.dto.response.GroupInfoResponse;
import com.codot.link.domains.group.dto.response.GroupSearchListResponse;
import com.codot.link.domains.group.dto.response.GroupSearchResponse;
import com.codot.link.domains.group.dto.response.MyGroupListResponse;
import com.codot.link.domains.group.dto.response.MyGroupResponse;
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

	public void modifyGroup(Long userId, Long groupId, GroupModifyRequest request) {
		Group group = findOneByUserIdAndGroupIdAndRole(userId, groupId, HOST.toString(), "본인 소유의 그룹만 수정할 수 있습니다.");
		group.updateInfo(request);
	}

	public void deleteGroup(Long userId, Long groupId, GroupDeleteRequest request) {
		verifyUserByEmail(userId, request);

		Group group = findOneByUserIdAndGroupIdAndRole(userId, groupId, HOST.toString(), "본인 소유의 그룹만 삭제할 수 있습니다.");

		groupUserRepository.deleteAll(groupUserRepository.findAllByGroup(group));
		groupRepository.delete(group);
	}

	private Group findOneByUserIdAndGroupIdAndRole(Long userId, Long groupId, String role, String detail) {
		return groupRepository.findByUserIdAndGroupIdAndRole(userId, groupId, role)
			.orElseThrow(() -> CustomException.of(GROUP_NOT_FOUND, detail));
	}

	private void verifyUserByEmail(Long userId, GroupDeleteRequest request) {
		User user = findUserByUserId(userId);
		if (!user.getEmail().equals(request.getEmail())) {
			throw CustomException.from(EMAIL_NOT_MATCH);
		}
	}

	public void groupJoinRequest(Long userId, GroupJoinRequest request) {
		checkUserEligibilityToJoinGroup(userId, request.getGroupId());

		User user = findUserByUserId(userId);
		Group group = findOne(request.getGroupId());

		checkExistingRequestForDuplicate(user, group);
		groupUserRepository.save(GroupUser.of(user, group, PARTICIPANT, false));
	}

	private void checkUserEligibilityToJoinGroup(Long userId, Long groupId) {
		if (!groupRepository.isGroupJoinable(userId, groupId)) {
			throw CustomException.from(INAPPROPRIATE_GROUP_JOIN_REQUEST);
		}
	}

	private void checkExistingRequestForDuplicate(User user, Group group) {
		Optional<GroupUser> optionalGroupUser = groupUserRepository.findByUserAndGroup(user, group);
		if (optionalGroupUser.isEmpty()) {
			return;
		}
		GroupUser groupUser = optionalGroupUser.get();
		throw (groupUser.isAccepted()) ? CustomException.from(ALREADY_ACCEPTED_GROUP_JOIN_REQUEST) :
			CustomException.from(EXISTING_GROUP_JOIN_REQUEST);
	}

	public MyGroupListResponse myGroupList(Long userId) {
		List<MyGroupResult> groups = groupRepository.findAllByUserAndAccepted(userId, true);

		Map<Boolean, List<MyGroupResult>> partitionedResults = partitionGroupsByRole(groups);

		List<MyGroupResponse> hosts = extractResponseByCondition(partitionedResults, true);
		List<MyGroupResponse> participants = extractResponseByCondition(partitionedResults, false);

		return MyGroupListResponse.of(hosts, participants);
	}

	private Map<Boolean, List<MyGroupResult>> partitionGroupsByRole(List<MyGroupResult> groups) {
		return groups.stream()
			.collect(Collectors.partitioningBy(group -> group.getRole().equals(HOST)));
	}

	private List<MyGroupResponse> extractResponseByCondition(Map<Boolean, List<MyGroupResult>> partitionedResults,
		boolean condition) {
		return partitionedResults.get(condition).stream()
			.map(MyGroupResponse::from)
			.toList();
	}

	public GroupSearchListResponse groupList(String searchText) {
		String regex = processSearchText(searchText);
		List<Group> allByRegex = groupRepository.findAllBySearchTextRegex(regex);

		List<GroupSearchResponse> groups = allByRegex.stream()
			.map(GroupSearchResponse::from)
			.toList();
		return GroupSearchListResponse.from(groups);
	}

	private String processSearchText(String searchText) {
		if (searchText == null) {
			return "";
		}
		return "(?i)" + searchText.trim().replaceAll(" +", "|");
	}
}
