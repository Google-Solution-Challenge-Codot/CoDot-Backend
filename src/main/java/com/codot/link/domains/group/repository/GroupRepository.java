package com.codot.link.domains.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.group.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	boolean existsByName(String name);
}
