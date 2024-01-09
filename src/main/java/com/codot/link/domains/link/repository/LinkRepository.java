package com.codot.link.domains.link.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.link.domain.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
