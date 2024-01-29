package com.codot.link.common.scheduling;

import static java.time.LocalDateTime.*;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.domains.auth.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SchedulerService {

	private final RefreshTokenRepository refreshTokenRepository;

	@Scheduled(cron = "0 0 * * * *")
	protected void deleteExpiredRefreshToken() {
		refreshTokenRepository.deleteAllByExpireAtBefore(now());
	}
}
