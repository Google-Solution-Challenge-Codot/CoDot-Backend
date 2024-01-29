package com.codot.link.domains.post.domain;

import java.util.ArrayList;
import java.util.List;

import com.codot.link.common.auditing.BaseEntity;
import com.codot.link.domains.comment.domain.Comment;
import com.codot.link.domains.group.domain.Group;
import com.codot.link.domains.post.dto.request.PostCreateRequest;
import com.codot.link.domains.post.dto.request.PostModifyRequest;
import com.codot.link.domains.user.domain.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();

	@Builder(access = AccessLevel.PRIVATE)
	private Post(String title, String content, User user, Group group) {
		this.title = title;
		this.content = content;
		this.user = user;
		this.group = group;
	}

	public static Post of(PostCreateRequest request, User user, Group group) {
		return Post.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.user(user)
			.group(group)
			.build();
	}

	public void updateInfo(PostModifyRequest request) {
		if (request.getTitle() != null) {
			this.title = request.getTitle();
		}
		if (request.getContent() != null) {
			this.content = request.getContent();
		}
	}
}
