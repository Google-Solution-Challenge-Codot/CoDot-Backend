package com.codot.link.domains.auth.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.auth.domain.RefreshToken;
import com.codot.link.domains.user.domain.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByUserAndToken(User user, String token);

	void deleteAllByExpireAtBefore(LocalDateTime now);

	Optional<RefreshToken> findByUser_Id(Long userId);
}
