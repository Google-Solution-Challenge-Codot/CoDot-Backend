package com.codot.link.domains.comment.domain;

import com.codot.link.common.auditing.BaseEntity;
import com.codot.link.domains.comment.dto.request.SubCommentCreateRequest;
import com.codot.link.domains.comment.dto.request.SubCommentModifyRequest;
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
public class SubComment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sub_comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id", nullable = false)
	private Comment comment;

	@Column(nullable = false)
	private String content;

	@Builder(access = AccessLevel.PRIVATE)
	private SubComment(User user, Comment comment, String content) {
		this.user = user;
		this.comment = comment;
		this.content = content;
	}

	public static SubComment of(User user, Comment comment, SubCommentCreateRequest request) {
		return SubComment.builder()
			.user(user)
			.comment(comment)
			.content(request.getContent())
			.build();
	}

	public void updateContent(SubCommentModifyRequest request) {
		if (request.getContent() != null) {
			this.content = request.getContent();
		}
	}
}
