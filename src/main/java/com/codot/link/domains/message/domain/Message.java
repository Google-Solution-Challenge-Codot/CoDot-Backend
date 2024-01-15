package com.codot.link.domains.message.domain;

import com.codot.link.common.auditing.BaseEntity;
import com.codot.link.domains.message.dto.request.MessageCreateRequest;
import com.codot.link.domains.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

	@Id
	@Column(name = "message_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", nullable = false)
	private User sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id", nullable = false)
	private User receiver;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Builder(access = AccessLevel.PRIVATE)
	private Message(User sender, User receiver, String title, String content) {
		this.sender = sender;
		this.receiver = receiver;
		this.title = title;
		this.content = content;
	}

	public static Message of(User sender, User receiver, MessageCreateRequest request) {
		return Message.builder()
			.sender(sender)
			.receiver(receiver)
			.title(request.getTitle())
			.content(request.getContent())
			.build();
	}
}
