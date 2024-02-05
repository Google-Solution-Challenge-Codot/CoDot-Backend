package com.codot.link.domains.message.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.message.domain.Message;
import com.codot.link.domains.user.domain.User;

public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findAllByReceiver_Id(Long receiverId);

	void deleteAllBySenderOrReceiver(User sender, User receiver);
}
