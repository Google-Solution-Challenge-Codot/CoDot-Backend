package com.codot.link.domains.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.auth.domain.LoginRecord;
import com.codot.link.domains.auth.domain.Provider;
import com.codot.link.domains.user.domain.User;

public interface LoginRecordRepository extends JpaRepository<LoginRecord, Long> {

	Optional<LoginRecord> findByOauthIdAndProvider(String oauthId, Provider provider);

	void deleteByUser(User user);
}
