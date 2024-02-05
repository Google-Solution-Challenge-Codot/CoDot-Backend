package com.codot.link.domains.group.domain;

import java.util.ArrayList;
import java.util.List;

import com.codot.link.common.auditing.BaseEntity;
import com.codot.link.domains.group.dto.request.GroupCreateRequest;
import com.codot.link.domains.group.dto.request.GroupModifyRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String description;

	@OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
	private List<GroupUser> groupUsers = new ArrayList<>();

	@Builder(access = AccessLevel.PRIVATE)
	private Group(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public static Group from(GroupCreateRequest request) {
		return Group.builder()
			.name(request.getName())
			.description(request.getDescription())
			.build();
	}

	public void updateInfo(GroupModifyRequest request) {
		if (request.getName() != null) {
			this.name = request.getName();
		}
		if (request.getDescription() != null) {
			this.description = request.getDescription();
		}
	}
}