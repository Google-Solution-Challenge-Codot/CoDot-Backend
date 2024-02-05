package com.codot.link.domains.fcm;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codot.link.domains.fcm.domain.FcmToken;
import com.codot.link.domains.user.domain.User;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

	Optional<FcmToken> findByUser(User user);

	@Query(value = "select ft.* from fcm_token ft "
		+ "join group_user gu on ft.user_id = gu.user_id "
		+ "where gu.group_id = :groupId and gu.accepted = true", nativeQuery = true)
	List<FcmToken> findAllByGroupId(@Param("groupId") Long groupId);

	void deleteByUser(User user);
}
