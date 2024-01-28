package com.codot.link.domains.comment.service;

import static com.codot.link.common.exception.model.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.comment.domain.Comment;
import com.codot.link.domains.comment.dto.request.CommentCreateRequest;
import com.codot.link.domains.comment.dto.request.CommentModifyRequest;
import com.codot.link.domains.comment.repository.CommentRepository;
import com.codot.link.domains.post.domain.Post;
import com.codot.link.domains.post.repository.PostRepository;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	public void createComment(Long userId, CommentCreateRequest request) {
		checkCommentWritingPermission(userId, request);

		User user = findUserByUserId(userId);
		Post post = findPostByPostId(request.getPostId());
		commentRepository.save(Comment.of(user, post, request));
	}

	private void checkCommentWritingPermission(Long userId, CommentCreateRequest request) {
		if (!postRepository.canUserAccessPost(userId, request.getPostId())) {
			throw CustomException.of(NOT_GROUP_MEMBER, "자신이 속한 그룹의 게시물에만 댓글을 달 수 있습니다.");
		}
	}

	private User findUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
	}

	private Post findPostByPostId(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> CustomException.from(POST_NOT_FOUND));
	}

	public void modifyComment(Long userId, Long commentId, CommentModifyRequest request) {
		Comment comment = findMyComment(userId, commentId);

		comment.updateContent(request);
	}

	public void deleteComment(Long userId, Long commentId) {
		Comment comment = findMyComment(userId, commentId);

		commentRepository.delete(comment);
	}

	private Comment findMyComment(Long userId, Long commentId) {
		return commentRepository.findByIdAndUser_Id(commentId, userId)
			.orElseThrow(() -> CustomException.from(NOT_COMMENT_WRITER));
	}
}
