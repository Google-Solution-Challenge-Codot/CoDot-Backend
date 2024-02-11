package com.codot.link.domains.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codot.link.domains.user.dao.OneHopDirectSearchResult;
import com.codot.link.domains.user.dao.ThreeHopDirectSearchResult;
import com.codot.link.domains.user.dao.TwoHopDirectSearchResult;
import com.codot.link.domains.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByNickname(String nickname);

	/*
	선택한 컬럼의 alias와 DAO 인터페이스의 get 메서드에 들어가는 이름이 동일해야 한다.(이 경우 둘 다 camel case로 동일)
	처음엔 alias를 snake case로 하고 get 메서드는 camel로 했더니 매핑이 제대로 되지 않았다. 그래서 alias도 camel로 수정하니 작동.
	 */

	@Query(value = "select l.to_id as firstHopId, l.connection_point as sourceFirstCp from link l "
		+ "where l.from_id = :userId and l.status = 'CONNECTED' "
		+ "and exists (select 1 from users u where u.nickname= :nickname and l.to_id = u.user_id) "
		+ "limit 1", nativeQuery = true)
	Optional<OneHopDirectSearchResult> findUserByDirectSearchFirstHop(@Param("userId") Long userId,
		@Param("nickname") String nickname);

	@Query(value =
		"select L1.to_id as firstHopId, L1.connection_point as sourceFirstCp, l2.to_id as secondHopId, l2.connection_point as firstSecondCp from "
			+ "(select * from link l1 where l1.from_id = :userId and l1.status = 'CONNECTED') as L1 "
			+ "join link l2 on L1.to_id = l2.from_id "
			+ "where l2.to_id <> :userId "
			+ "and l2.status = 'CONNECTED' "
			+ "and not exists (select 1 from link l3 where l3.from_id = l2.to_id and l3.to_id = :userId and l3.status = 'CONNECTED') "
			+ "and exists (select 1 from users u where u.nickname = :nickname and u.user_id = l2.to_id) "
			+ "limit 1", nativeQuery = true)
	Optional<TwoHopDirectSearchResult> findUserByDirectSearchTwoHop(@Param("userId") Long userId,
		@Param("nickname") String nickname);

	@Query(value = "with L1 as ("
		+ "select l2.to_id as firstHopId, l2.connection_point as sourceFirstCp, l3.to_id as secondHopId, l3.connection_point as firstSecondCp from "
		+ "(select * from link l1 where l1.from_id = :userId and l1.status = 'CONNECTED') as l2 "
		+ "join link l3 on l2.to_id = l3.from_id "
		+ "where l3.to_id <> :userId "
		+ "and l3.status = 'CONNECTED' "
		+ "and not exists (select 1 from link l4 where l4.from_id = l3.to_id and l4.to_id = :userId and l4.status = 'CONNECTED')"
		+ ") "
		+ "select L1.*, l5.to_id as thirdHopId, l5.connection_point as secondThirdCp from L1 "
		+ "join link l5 on l5.from_id = L1.secondHopId "
		+ "where l5.status = 'CONNECTED' "
		+ "and not exists (select 1 from L1 where L1.firstHopId = l5.to_id) "
		+ "and not exists (select 1 from L1 where L1.secondHopId = l5.to_id) "
		+ "and exists (select 1 from users u where u.nickname = :nickname and u.user_id = l5.to_id) "
		+ "limit 1", nativeQuery = true)
	Optional<ThreeHopDirectSearchResult> findUserByDirectSearchThreeHop(@Param("userId") Long userId,
		@Param("nickname") String nickname);

	@Query(value = "select u.* from users u "
		+ "where exists (select 1 from link l where l.from_id = :userId and l.to_id = u.user_id and l.status = 'CONNECTED')", nativeQuery = true)
	List<User> findMyFriends(@Param("userId") Long userId);

	@Query(value = "select U1.* from (select u.* from users u "
		+ "where exists (select 1 from link l1 where l1.from_id = :userId and l1.to_id = u.user_id and l1.status = 'CONNECTED')) as U1 "
		+ "where (U1.user_id not in :previousHops) "
		+ "and not exists (select 1 from link l2 where l2.from_id in :previousHops and l2.to_id = U1.user_id)", nativeQuery = true)
	List<User> findMyFriendsByFriendSearch(@Param("userId") Long userId,
		@Param("previousHops") List<Long> previousHops);
}
