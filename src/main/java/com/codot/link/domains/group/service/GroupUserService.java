package com.codot.link.domains.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.domains.group.repository.GroupUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupUserService {

	private final GroupUserRepository groupUserRepository;
}
