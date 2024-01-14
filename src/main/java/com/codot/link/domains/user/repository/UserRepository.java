package com.codot.link.domains.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByNickname(String nickname);
}
