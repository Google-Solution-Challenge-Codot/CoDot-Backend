package com.codot.link.domains.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.group.domain.Group;
import com.codot.link.domains.group.domain.GroupUser;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {

	List<GroupUser> findAllByGroup(Group group);
}
