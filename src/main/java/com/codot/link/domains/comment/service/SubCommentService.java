package com.codot.link.domains.comment.service;

import static com.codot.link.common.exception.model.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.comment.domain.Comment;
import com.codot.link.domains.comment.domain.SubComment;
import com.codot.link.domains.comment.dto.request.SubCommentCreateRequest;
import com.codot.link.domains.comment.repository.CommentRepository;
import com.codot.link.domains.comment.repository.SubCommentRepository;
import com.codot.link.domains.post.repository.PostRepository;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SubCommentService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final SubCommentRepository subCommentRepository;

	public void createSubComment(Long userId, SubCommentCreateRequest request) {
		checkSubCommentWritingPermission(userId, request);

		User user = findUserByUserId(userId);
		Comment comment = findCommentByCommentId(request.getCommentId());
		subCommentRepository.save(SubComment.of(user, comment, request));
	}

	private void checkSubCommentWritingPermission(Long userId, SubCommentCreateRequest request) {
		if (!postRepository.canUserAccessPost(userId, request.getPostId())) {
			throw CustomException.of(NOT_GROUP_MEMBER, "자신이 속한 그룹의 게시물에만 대댓글을 달 수 있습니다.");
		}
	}

	private User findUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
	}

	private Comment findCommentByCommentId(Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(() -> CustomException.from(COMMENT_NOT_FOUND));
	}
}
