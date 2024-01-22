package com.codot.link.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.codot.link.common.auth.filter.JwtAuthenticationFilter;
import com.codot.link.common.auth.jwt.JwtUtils;
import com.codot.link.common.exception.filter.CustomExceptionHandlingFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtUtils jwtUtils;
	private final ObjectMapper objectMapper;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.csrf(AbstractHttpConfigurer::disable)         // csrf disable. jwt를 사용하고 세션을 사용하지 않으므로 비활성화
			.formLogin(AbstractHttpConfigurer::disable)    // formLogin 방식의 인증은 사용하지 않을 예정이므로 비활성화
			.httpBasic(AbstractHttpConfigurer::disable);   // httpBasic 방식의 인증은 사용하지 않을 예정이므로 비활성화

		http
			.authorizeHttpRequests(request -> request
				.requestMatchers("/api/v1/**").authenticated()
				.requestMatchers("/login", "/").permitAll()
				.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				.anyRequest().authenticated());

		http
			.headers(header -> header
				.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

		http
			.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(customExceptionHandlingFilter(), JwtAuthenticationFilter.class);

		// 세션 사용 방식도 STATELESS로 설정함으로써 매 요청마다 임시 세션만 사용하고 영구 세션 저장소는 사용하지 않음
		http
			.sessionManagement(management -> management
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtUtils);
	}

	@Bean
	public CustomExceptionHandlingFilter customExceptionHandlingFilter() {
		return new CustomExceptionHandlingFilter(objectMapper);
	}
}
