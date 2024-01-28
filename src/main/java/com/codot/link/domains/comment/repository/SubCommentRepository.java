package com.codot.link.domains.comment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.comment.domain.SubComment;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {

	Optional<SubComment> findByIdAndUser_Id(Long subCommentId, Long userId);
}
