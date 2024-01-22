package com.codot.link.domains.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codot.link.domains.auth.domain.LoginRecord;

public interface LoginRecordRepository extends JpaRepository<LoginRecord, Long> {
}
