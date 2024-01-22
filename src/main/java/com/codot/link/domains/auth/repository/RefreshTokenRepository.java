package com.codot.link.domains.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.auth.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
