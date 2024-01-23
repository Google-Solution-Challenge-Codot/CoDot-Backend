package com.codot.link.domains.auth.service;

import static com.codot.link.domains.auth.domain.IdType.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.auth.jwt.JwtUtils;
import com.codot.link.domains.auth.domain.LoginRecord;
import com.codot.link.domains.auth.domain.Provider;
import com.codot.link.domains.auth.domain.RefreshToken;
import com.codot.link.domains.auth.dto.request.IssueTokenRequest;
import com.codot.link.domains.auth.dto.response.IdResponse;
import com.codot.link.domains.auth.dto.response.IssueTokenResponse;
import com.codot.link.domains.auth.repository.LoginRecordRepository;
import com.codot.link.domains.auth.repository.RefreshTokenRepository;
import com.codot.link.domains.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

	private final JwtUtils jwtUtils;
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
}
