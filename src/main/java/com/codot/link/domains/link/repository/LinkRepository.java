package com.codot.link.domains.link.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codot.link.domains.link.domain.Link;
import com.codot.link.domains.link.domain.Status;
import com.codot.link.domains.user.domain.User;

public interface LinkRepository extends JpaRepository<Link, Long> {

	boolean existsByFromAndTo(User from, User to);

	List<Link> findAllByTo_IdAndStatus(Long toId, Status status);

	Optional<Link> findByFromAndTo(User from, User to);

	List<Link> findAllByFrom_IdAndStatus(Long fromId, Status status);

	Optional<Link> findByFrom_IdAndTo_IdAndStatus(Long fromId, Long toId, Status status);

	@Query(value =
		"select l1.* from ("
			+ "select u.* from users u where exists (select 1 from link l where l.from_id = :userId and l.to_id = u.user_id and l.status = 'CONNECTED')"
			+ ") as u1 "
			+ "join link l1 on l1.from_id = u1.user_id "
			+ "where l1.to_id <> :userId "
			+ "and not exists "
			+ "(select 1 from link l2 where l2.from_id = :userId and l2.to_id = l1.to_id and l2.status = 'CONNECTED') "
			+ "and l1.status = 'CONNECTED' "
			+ "order by random() "
			+ "limit 5", nativeQuery = true)
	List<Link> findTwoHops(@Param("userId") Long userId);

	void deleteAllByFromOrTo(User from, User to);
}
