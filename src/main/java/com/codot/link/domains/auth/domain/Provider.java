package com.codot.link.domains.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {

	GOOGLE("google");

	private final String description;
}
