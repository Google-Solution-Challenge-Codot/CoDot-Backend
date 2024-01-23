package com.codot.link.common.auth.filter;

import static com.codot.link.common.exception.model.ErrorCode.*;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codot.link.common.auth.jwt.JwtUtils;
import com.codot.link.common.exception.model.CustomException;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = getToken(request);

		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		validateToken(token);

		Authentication authentication = checkHeaderAndCreateAuthentication(request, token);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
			return null;
		}
		return authorizationHeader.substring(7);
	}

	private void validateToken(String token) {
		try {
			jwtUtils.validateToken(token);
		} catch (JwtException e) {
			log.info("Invalid Token");
			throw CustomException.of(TOKEN_NOT_VALID, e.getMessage());
		}
	}

	private Authentication checkHeaderAndCreateAuthentication(HttpServletRequest request, String token) {
		if (request.getHeader("user-id") != null) {
			return new UsernamePasswordAuthenticationToken(jwtUtils.extractNickname(token), token, null);
		} else if (request.getHeader("login-record-id") != null) {
			return new UsernamePasswordAuthenticationToken("Unregistered User", token, null);
		}

		throw CustomException.from(ID_NOT_PRESENT);
	}
}
