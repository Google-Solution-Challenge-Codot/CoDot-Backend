package com.codot.link.domains.post.service;

import static com.codot.link.common.exception.model.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.group.domain.Group;
import com.codot.link.domains.group.repository.GroupRepository;
import com.codot.link.domains.group.repository.GroupUserRepository;
import com.codot.link.domains.post.domain.Post;
import com.codot.link.domains.post.dto.request.PostCreateRequest;
import com.codot.link.domains.post.dto.request.PostModifyRequest;
import com.codot.link.domains.post.dto.response.PostInfoListResponse;
import com.codot.link.domains.post.dto.response.PostInfoResponse;
import com.codot.link.domains.post.dto.response.PostResponse;
import com.codot.link.domains.post.repository.PostRepository;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final GroupRepository groupRepository;
	private final GroupUserRepository groupUserRepository;

	public void createPost(Long userId, PostCreateRequest request) {
		CheckPostRegistrationPermission(userId, request);

		User user = findUserByUserId(userId);
		Group group = findGroupByGroupId(request);
		postRepository.save(Post.of(request, user, group));
	}

	private void CheckPostRegistrationPermission(Long userId, PostCreateRequest request) {
		if (!groupUserRepository.existsByUser_IdAndGroup_IdAndAccepted(userId, request.getGroupId(), true)) {
			throw CustomException.of(NOT_GROUP_MEMBER, "자신이 속한 그룹에만 게시물을 등록할 수 있습니다.");
		}
	}

	private User findUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
	}

	private Group findGroupByGroupId(PostCreateRequest request) {
		return groupRepository.findById(request.getGroupId())
			.orElseThrow(() -> CustomException.from(GROUP_NOT_FOUND));
	}

	public PostInfoResponse postInfo(Long userId, Long postId) {
		checkPostReadingPermission(userId, postId);

		Post post = findOne(postId);
		return PostInfoResponse.from(post);
	}

	private void checkPostReadingPermission(Long userId, Long postId) {
		if (!postRepository.canUserAccessPost(userId, postId)) {
			throw CustomException.of(NOT_GROUP_MEMBER, "자신이 속한 그룹의 게시물만 조회할 수 있습니다.");
		}
	}

	public PostInfoListResponse postList(Long userId, Long groupId) {
		checkUserParticipationInGroup(userId, groupId);

		List<PostResponse> posts = postRepository.findAllByGroup_Id(groupId).stream()
			.map(PostResponse::from)
			.toList();
		return PostInfoListResponse.from(posts);
	}

	private void checkUserParticipationInGroup(Long userId, Long groupId) {
		if (!groupUserRepository.existsByUser_IdAndGroup_IdAndAccepted(userId, groupId, true)) {
			throw CustomException.of(NOT_GROUP_MEMBER, "자신이 속한 그룹의 게시물들만 조회할 수 있습니다.");
		}
	}

	public void modifyPost(Long userId, Long postId, PostModifyRequest request) {
		Post post = findMyPost(userId, postId);

		post.updateInfo(request);
	}

	public void deletePost(Long userId, Long postId) {
		Post post = findMyPost(userId, postId);

		postRepository.deleteById(postId);
	}

	private Post findMyPost(Long userId, Long postId) {
		return postRepository.findByIdAndUser_Id(postId, userId)
			.orElseThrow(() -> CustomException.from(NOT_POST_WRITER));
	}

	private Post findOne(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> CustomException.from(POST_NOT_FOUND));
	}
}
