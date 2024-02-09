package com.codot.link.domains.user.repository;

import static com.codot.link.domains.link.domain.Status.*;
import static com.codot.link.domains.user.domain.GraduationStatus.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.codot.link.domains.link.domain.Link;
import com.codot.link.domains.link.repository.LinkRepository;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.dto.request.UserSignupRequest;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	LinkRepository linkRepository;

	@Test
	@DisplayName("특정 사용자와 CONNECTED 친구 관계에 있는 모든 사용자를 조회할 수 있어야 한다")
	void 특정_사용자와_CONNECTED_친구_관계에_있는_모든_사용자를_조회할_수_있어야_한다() {
		//given
		UserSignupRequest requestA = UserSignupRequest.of("userA", "email", "nicknameA", "HONGIK", "CE",
			"hello", UNDERGRADUATE);
		UserSignupRequest requestB = UserSignupRequest.of("userB", "email", "nicknameB", "HONGIK", "CE",
			"hello", UNDERGRADUATE);
		UserSignupRequest requestC = UserSignupRequest.of("userC", "email", "nicknameC", "HONGIK", "CE",
			"hello", UNDERGRADUATE);

		User userA = userRepository.save(User.from(requestA));
		User userB = userRepository.save(User.from(requestB));
		User userC = userRepository.save(User.from(requestC));

		Link fromAToB = Link.of(userA, userB, CONNECTED);
		Link fromBToA = Link.of(userB, userA, CONNECTED);
		Link fromBToC = Link.of(userB, userC, CONNECTED);
		Link fromCToB = Link.of(userC, userB, CONNECTED);
		Link fromAToC = Link.of(userA, userC, PROCESSING);
		Link fromCToA = Link.of(userC, userA, PROCESSING);
		linkRepository.saveAll(List.of(fromAToB, fromBToA, fromAToC, fromCToA, fromBToC, fromCToB));

		//when
		List<User> friends = userRepository.findMyFriends(userA.getId());

		//then
		assertThat(friends.size()).isEqualTo(1);
		assertThat(friends).contains(userB);
		assertThat(friends).doesNotContain(userC);
	}
}