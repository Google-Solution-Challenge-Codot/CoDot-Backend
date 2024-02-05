package com.codot.link.domains.group.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codot.link.domains.group.dao.MyGroupResult;
import com.codot.link.domains.group.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	boolean existsByName(String name);

	@Query(value = "select g.* from groups g "
		+ "where exists (select 1 from group_user gu where gu.user_id = :userId and gu.group_id = :groupId and gu.role = :role)", nativeQuery = true)
	Optional<Group> findByUserIdAndGroupIdAndRole(@Param("userId") Long userId, @Param("groupId") Long groupId,
		@Param("role") String role);

	@Query(value = "select count(*) > 0 from "
		+ "(select u.user_id from users u "
		+ "where exists (select 1 from link l where l.from_id = :userId and l.to_id = u.user_id and l.status = 'CONNECTED') "
		+ "union "
		+ "select distinct l1.to_id as user_id from (select u2.* from users u2 where exists (select 1 from link l3 where l3.from_id = :userId and l3.to_id = u2.user_id and l3.status = 'CONNECTED')) as u1 "
		+ "join link l1 on l1.from_id = u1.user_id "
		+ "where l1.to_id <> :userId and l1.status = 'CONNECTED' "
		+ "and not exists "
		+ "(select 1 from link l2 where (l2.from_id = :userId and l2.to_id = l1.to_id and l2.status = 'CONNECTED') or "
		+ "(l2.from_id = l1.to_id and l2.to_id = :userId and l2.status = 'CONNECTED'))"
		+ ") as u3 "
		+ "where exists (select 1 from group_user gu where gu.user_id = u3.user_id and gu.group_id = :groupId and gu.accepted = true)", nativeQuery = true)
	boolean isGroupJoinable(@Param("userId") Long userId, @Param("groupId") Long groupId);

	@Query(value = "select g.group_id as groupId, g.name as groupName, g.description, gu.role from groups g "
		+ "join group_user gu on g.group_id = gu.group_id "
		+ "where gu.user_id = :userId and gu.accepted = :accepted", nativeQuery = true)
	List<MyGroupResult> findAllByUserAndAccepted(@Param("userId") Long userId, @Param("accepted") boolean accepted);

	// TODO: postgresql로 변경 시 쿼리 수정할 것
	@Query(value = "select * from groups g "
		+ "where g.name REGEXP :regex "
		+ "or g.description REGEXP :regex", nativeQuery = true)
	List<Group> findAllBySearchTextRegex(@Param("regex") String regex);

	@Query(value = "select * from groups g "
		+ "where exists (select 1 from group_user gu where gu.user_id = :userId and gu.group_id = gu.group_id and gu.role = 'HOST')", nativeQuery = true)
	List<Group> findAllByHostUser(@Param("userId") Long userId);
}
