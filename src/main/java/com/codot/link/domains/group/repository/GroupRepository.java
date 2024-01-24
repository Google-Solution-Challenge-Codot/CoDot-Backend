package com.codot.link.domains.group.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codot.link.domains.group.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	boolean existsByName(String name);

	@Query(value = "select g.* from groups g "
		+ "where exists (select 1 from group_user gu where gu.user_id = :userId and gu.group_id = :groupId and gu.role = :role)", nativeQuery = true)
	Optional<Group> findByUserIdAndGroupIdAndRole(@Param("userId") Long userId, @Param("groupId") Long groupId,
		@Param("role") String role);
}
