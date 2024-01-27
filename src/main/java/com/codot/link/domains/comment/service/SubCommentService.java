package com.codot.link.domains.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.domains.comment.repository.SubCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SubCommentService {

	private final SubCommentRepository subCommentRepository;
}
