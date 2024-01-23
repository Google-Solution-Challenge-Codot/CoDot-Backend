package com.codot.link.common.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.codot.link.domains.user.domain.User;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtils {

	private final SecretKey secretKey;
	private static final Duration JWT_EXPIRATION_FOR_REGISTERED = Duration.ofMinutes(30);
	private static final Duration JWT_EXPIRATION_FOR_UNREGISTERED = Duration.ofMinutes(5);
	private static final Duration JWT_EXPIRATION_FOR_REFRESH = Duration.ofDays(7);

	// yml 파일에 정의한 (String) secret key를 이용하여 실제 jwt 암호/복호화 과정에서 사용할 수 있는 Key 객체 생성
	public JwtUtils(@Value("${jwt.secret_key}") String secretKey) {
		this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	// jwt에서 사용자 닉네임 정보 추출
	public String extractNickname(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("nickname", String.class);
	}

	// jwt 만료 여부 확인
	public void validateToken(String token) {
		Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token);
	}

	// Access Token 생성
	public String generateAccessToken(User user) {
		JwtBuilder builder = Jwts.builder()
			.issuedAt(new Date());

		if (user != null) {
			builder
				.claim("nickname", user.getNickname())
				.expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_FOR_REGISTERED.toMillis()));
		} else {
			builder
				.expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_FOR_UNREGISTERED.toMillis()));
		}

		return builder
			.signWith(secretKey)
			.compact();
	}

	// Refresh Token 생성
	public String generateRefreshToken() {
		return Jwts.builder()
			.issuedAt(new Date())
			.signWith(secretKey)
			.expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_FOR_REFRESH.toMillis()))
			.compact();
	}
}
