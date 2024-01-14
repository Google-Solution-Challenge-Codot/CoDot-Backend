package com.codot.link.domains.link.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.link.domain.Link;
import com.codot.link.domains.user.domain.User;

public interface LinkRepository extends JpaRepository<Link, Long> {

	boolean existsByFromAndTo(User from, User to);
}
