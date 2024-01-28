package com.codot.link.domains.comment.domain;

import com.codot.link.domains.comment.dto.request.CommentCreateRequest;
import com.codot.link.domains.post.domain.Post;
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
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@Column(nullable = false)
	private String content;

	@Builder(access = AccessLevel.PRIVATE)
	private Comment(User user, Post post, String content) {
		this.user = user;
		this.post = post;
		this.content = content;
	}

	public static Comment of(User user, Post post, CommentCreateRequest request) {
		return Comment.builder()
			.user(user)
			.post(post)
			.content(request.getContent())
			.build();
	}
}