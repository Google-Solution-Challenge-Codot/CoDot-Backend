package com.codot.link.domains.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.message.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}