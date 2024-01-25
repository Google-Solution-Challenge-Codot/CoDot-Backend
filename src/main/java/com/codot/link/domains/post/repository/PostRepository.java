package com.codot.link.domains.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
