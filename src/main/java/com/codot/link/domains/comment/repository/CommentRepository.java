package com.codot.link.domains.comment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Optional<Comment> findByIdAndUser_Id(Long commentId, Long userId);
}
