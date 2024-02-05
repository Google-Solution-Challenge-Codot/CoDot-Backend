package com.codot.link.domains.post.service;

import static com.codot.link.common.exception.model.ErrorCode.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.auditing.BaseEntity;
import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.comment.domain.Comment;
import com.codot.link.domains.comment.domain.SubComment;
import com.codot.link.domains.group.domain.Group;
import com.codot.link.domains.group.repository.GroupRepository;
import com.codot.link.domains.group.repository.GroupUserRepository;
import com.codot.link.domains.post.domain.Post;
import com.codot.link.domains.post.dto.request.PostCreateRequest;
import com.codot.link.domains.post.dto.request.PostModifyRequest;
import com.codot.link.domains.post.dto.response.CommentResponse;
import com.codot.link.domains.post.dto.response.PostInfoListResponse;
import com.codot.link.domains.post.dto.response.PostInfoResponse;
import com.codot.link.domains.post.dto.response.PostResponse;
import com.codot.link.domains.post.dto.response.SubCommentResponse;
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
		List<Comment> comments = post.getComments();
		if (comments.isEmpty()) {
			return PostInfoResponse.of(post, new ArrayList<>());
		}
		comments.sort(getCreatedDateComparator());

		List<CommentResponse> commentResponses = convertCommentToCommentResponse(comments);
		return PostInfoResponse.of(post, commentResponses);
	}

	private void checkPostReadingPermission(Long userId, Long postId) {
		if (!postRepository.canUserAccessPost(userId, postId)) {
			throw CustomException.of(NOT_GROUP_MEMBER, "자신이 속한 그룹의 게시물만 조회할 수 있습니다.");
		}
	}

	private List<CommentResponse> convertCommentToCommentResponse(List<Comment> comments) {
		return comments.stream()
			.collect(Collectors.toMap(c -> c, c -> convertSubCommentToSubCommentResponse(c.getSubComments()),
				(original, replacement) -> original, LinkedHashMap::new))
			.entrySet().stream()
			.map(entry -> CommentResponse.of(entry.getKey(), entry.getValue()))
			.toList();
	}

	private List<SubCommentResponse> convertSubCommentToSubCommentResponse(List<SubComment> subComments) {
		subComments.sort(getCreatedDateComparator());
		return subComments.stream()
			.map(SubCommentResponse::from)
			.toList();
	}

	private Comparator<BaseEntity> getCreatedDateComparator() {
		return (o1, o2) -> {
			if (o1.getCreatedAt().isBefore(o2.getCreatedAt())) {
				return -1;
			}
			if (o1.getCreatedAt().isAfter(o2.getCreatedAt())) {
				return 1;
			}
			return 0;
		};
	}

	public PostInfoListResponse postList(Long userId, Long groupId) {
		checkUserParticipationInGroup(userId, groupId);

		List<PostResponse> posts = postRepository.findAllByGroup_Id(groupId).stream()
			.sorted(getCreatedDateReverseComparator())
			.map(PostResponse::from)
			.toList();
		return PostInfoListResponse.from(posts);
	}

	private Comparator<BaseEntity> getCreatedDateReverseComparator() {
		return (p1, p2) -> {
			if (p1.getCreatedAt().isBefore(p2.getCreatedAt())) {
				return 1;
			}
			if (p1.getCreatedAt().isAfter(p2.getCreatedAt())) {
				return -1;
			}
			return 0;
		};
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
