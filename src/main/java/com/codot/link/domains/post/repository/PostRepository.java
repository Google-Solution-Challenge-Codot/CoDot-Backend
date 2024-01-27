package com.codot.link.domains.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codot.link.domains.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Query(value = "select count(*) > 0 from posts p "
		+ "join groups g on p.group_id = g.group_id "
		+ "where p.post_id = :postId "
		+ "and exists (select 1 from group_user gu where gu.group_id = g.group_id and gu.user_id = :userId and gu.accepted = true)", nativeQuery = true)
	boolean canUserAccessPost(@Param("userId") Long userId, @Param("postId") Long postId);

	List<Post> findAllByGroup_Id(Long groupId);

	Optional<Post> findByIdAndUser_Id(Long postId, Long userId);
}
