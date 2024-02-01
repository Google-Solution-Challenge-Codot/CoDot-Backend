package com.codot.link.domains.group.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.group.domain.Group;
import com.codot.link.domains.group.domain.GroupUser;
import com.codot.link.domains.group.domain.Role;
import com.codot.link.domains.user.domain.User;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {

	List<GroupUser> findAllByGroup(Group group);

	Optional<GroupUser> findByUserAndGroup(User user, Group group);

	boolean existsByUser_IdAndGroup_IdAndAccepted(Long userId, Long groupId, boolean accepted);

	boolean existsByUser_IdAndGroup_IdAndRole(Long userId, Long groupId, Role role);

	List<GroupUser> findAllByGroup_IdAndAccepted(Long groupId, boolean accepted);
}
