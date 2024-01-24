package com.codot.link.domains.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.group.domain.GroupUser;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
}
