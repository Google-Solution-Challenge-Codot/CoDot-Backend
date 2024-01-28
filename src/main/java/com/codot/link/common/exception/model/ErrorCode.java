package com.codot.link.common.exception.model;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	//400 BAD_REQUEST
	EMAIL_NOT_MATCH(BAD_REQUEST, "이메일이 일치하지 않습니다."),
	ID_NOT_PRESENT(BAD_REQUEST, "헤더에 어떠한 id도 존재하지 않습니다."),

	//401 UNAUTHORIZED
	TOKEN_NOT_VALID(UNAUTHORIZED, "토큰이 유효하지 않습니다."),

	// 404 NOT_FOUND
	USER_NOT_FOUND(NOT_FOUND, "존재하지 않는 사용자입니다."),
	LINK_NOT_FOUND(NOT_FOUND, "존재하지 않는 LINK입니다."),
	LOGIN_RECORD_NOT_FOUND(NOT_FOUND, "존재하지 않는 Login Record입니다."),
	GROUP_NOT_FOUND(NOT_FOUND, "존재하지 않는 그룹입니다."),
	GROUP_USER_NOT_FOUND(NOT_FOUND, "존재하지 않는 그룹 가입 요청입니다."),
	POST_NOT_FOUND(NOT_FOUND, "존재하지 않는 게시물입니다."),
	COMMENT_NOT_FOUND(NOT_FOUND, "존재하지 않는 댓글입니다."),
	SUB_COMMENT_NOT_FOUND(NOT_FOUND, "존재하지 않는 대댓글입니다."),

	// 409 CONFLICT
	DUPLICATE_USER_NICKNAME(CONFLICT, "이미 존재하는 닉네임입니다."),
	DUPLICATE_GROUP_NAME(CONFLICT, "이미 존재하는 그룹 이름입니다."),
	ALREADY_ACCEPTED_FRIEND_REQUEST(CONFLICT, "이미 수락된 친구 추가 요청입니다."),
	ALREADY_ACCEPTED_GROUP_JOIN_REQUEST(CONFLICT, "이미 해당 그룹에 참가한 상태입니다."),
	EXISTING_FRIEND_REQUEST(CONFLICT, "친구 추가 요청이 이미 존재합니다."),
	EXISTING_GROUP_JOIN_REQUEST(CONFLICT, "그룹 가입 요청이 이미 존재합니다."),
	INAPPROPRIATE_GROUP_JOIN_REQUEST(CONFLICT, "자신 기준 2 hop 이내의 사용자가 속한 그룹에만 가입 신청을 보낼 수 있습니다."),
	NOT_GROUP_MEMBER(CONFLICT, "해당 그룹의 멤버가 아닙니다."),
	NOT_POST_WRITER(CONFLICT, "해당 게시물의 작성자가 아닙니다."),
	NOT_COMMENT_WRITER(CONFLICT, "해당 댓글의 작성자가 아닙니다."),
	REFRESHTOKEN_NOT_MATCH(CONFLICT, "Refresh Token이 일치하지 않습니다.");

	private final String message;
	private final int statusCode;

	ErrorCode(HttpStatus status, String message) {
		this.statusCode = status.value();
		this.message = message;
	}
}
