package com.codot.link.domains.auth.service;

import static com.codot.link.common.exception.model.ErrorCode.*;
import static com.codot.link.domains.auth.domain.IdType.*;
import static java.time.LocalDateTime.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.auth.jwt.JwtUtils;
import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.auth.domain.LoginRecord;
import com.codot.link.domains.auth.domain.Provider;
import com.codot.link.domains.auth.domain.RefreshToken;
import com.codot.link.domains.auth.dto.request.IssueTokenRequest;
import com.codot.link.domains.auth.dto.response.IdResponse;
import com.codot.link.domains.auth.dto.response.IssueTokenResponse;
import com.codot.link.domains.auth.repository.LoginRecordRepository;
import com.codot.link.domains.auth.repository.RefreshTokenRepository;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

	private final JwtUtils jwtUtils;
	private final UserRepository userRepository;
	private final LoginRecordRepository loginRecordRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	public IssueTokenResponse issueJwt(IssueTokenRequest request) {
		LoginRecord loginRecord = getLoginRecord(request.getOauthId(), request.getProvider());
		User user = loginRecord.getUser();

		String accessToken = jwtUtils.generateAccessToken(user);
		String refreshToken = getRefreshTokenBasedOnRegistered(loginRecord, user);
		IdResponse idResponse = createIdResponseBasedOnRegistered(loginRecord, user);

		return IssueTokenResponse.of(idResponse, accessToken, refreshToken);
	}

	private LoginRecord getLoginRecord(String oauthId, Provider provider) {
		return loginRecordRepository.findByOauthIdAndProvider(oauthId, provider)
			.orElseGet(() -> loginRecordRepository.save(LoginRecord.of(oauthId, provider)));
	}

	private IdResponse createIdResponseBasedOnRegistered(LoginRecord loginRecord, User user) {
		if (loginRecord.isRegistered()) {
			return IdResponse.of(USER_ID, user.getId());
		}
		return IdResponse.of(LOGIN_RECORD_ID, loginRecord.getId());
	}

	private String getRefreshTokenBasedOnRegistered(LoginRecord loginRecord, User user) {
		if (loginRecord.isRegistered()) {
			return refreshTokenRepository.save(RefreshToken.of(user, jwtUtils.generateRefreshToken()))
				.getToken();
		}
		return null;
	}

	public String reissueJwt(Long userId, String refreshToken) {
		User user = findUserByUserId(userId);

		checkRefreshToken(user, refreshToken);
		return jwtUtils.generateAccessToken(user);
	}

	private void checkRefreshToken(User user, String refreshToken) {
		RefreshToken token = findRefreshTokenByUserAndToken(user, refreshToken);
		checkTokenExpiry(token);
	}

	private RefreshToken findRefreshTokenByUserAndToken(User user, String refreshToken) {
		return refreshTokenRepository.findByUserAndToken(user, refreshToken.substring(7))
			.orElseThrow(() -> CustomException.from(REFRESH_TOKEN_NOT_MATCH));
	}

	private void checkTokenExpiry(RefreshToken token) {
		if (token.getExpireAt().isBefore(now())) {
			refreshTokenRepository.delete(token);
			throw CustomException.from(REFRESH_TOKEN_EXPIRED);
		}
	}

	public void deleteJwt(Long userId) {
		RefreshToken token = refreshTokenRepository.findByUser_Id(userId)
			.orElseThrow(() -> CustomException.from(REFRESH_TOKEN_NOT_FOUND));
		
		refreshTokenRepository.delete(token);
	}

	private User findUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
	}

}
