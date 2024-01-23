package com.codot.link.domains.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.auth.domain.RefreshToken;
import com.codot.link.domains.user.domain.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	boolean existsByUserAndToken(User user, String token);
}
