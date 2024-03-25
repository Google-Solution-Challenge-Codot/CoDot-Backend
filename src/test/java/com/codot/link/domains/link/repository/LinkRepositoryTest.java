package com.codot.link.domains.link.repository;

import static com.codot.link.domains.link.domain.Status.*;
import static com.codot.link.domains.user.domain.GraduationStatus.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.codot.link.domains.link.domain.Link;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.dto.request.UserSignupRequest;
import com.codot.link.domains.user.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("local-test")
class LinkRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	LinkRepository linkRepository;

	User userA;
	User userB;
	User userC;
	User userD;
	User userE;

	// === Test Data Setup === //
	@BeforeEach
	void setTestData() {
		UserSignupRequest requestA = UserSignupRequest.of("userA", "email", "nicknameA", "HONGIK", "CE",
			"hello", UNDERGRADUATE);
		UserSignupRequest requestB = UserSignupRequest.of("userB", "email", "nicknameB", "HONGIK", "CE",
			"hello", UNDERGRADUATE);
		UserSignupRequest requestC = UserSignupRequest.of("userC", "email", "nicknameC", "HONGIK", "CE",
			"hello", UNDERGRADUATE);
		UserSignupRequest requestD = UserSignupRequest.of("userD", "email", "nicknameD", "HONGIK", "CE",
			"hello", UNDERGRADUATE);
		UserSignupRequest requestE = UserSignupRequest.of("userE", "email", "nicknameE", "HONGIK", "CE",
			"hello", UNDERGRADUATE);

		userA = userRepository.save(User.from(requestA));
		userB = userRepository.save(User.from(requestB));
		userC = userRepository.save(User.from(requestC));
		userD = userRepository.save(User.from(requestD));
		userE = userRepository.save(User.from(requestE));
	}

	// === findTwoHops Test === //
	@Test
	@DisplayName("특정 사용자와 2 hop 떨어진 사용자들을 랜덤으로 조회할 수 있어야 한다")
	void 특정_사용자와_2_hop_떨어진_사용자들을_랜덤으로_조회할_수_있어야_한다() {
		//given
		Link fromAToB = Link.of(userA, userB, CONNECTED);
		Link fromBToA = Link.of(userB, userA, CONNECTED);
		Link fromAToC = Link.of(userA, userC, CONNECTED);
		Link fromCToA = Link.of(userC, userA, CONNECTED);
		Link fromBToC = Link.of(userB, userC, CONNECTED);
		Link fromCToB = Link.of(userC, userB, CONNECTED);
		Link fromBToD = Link.of(userB, userD, CONNECTED);
		Link fromDToB = Link.of(userD, userB, CONNECTED);
		Link fromEToC = Link.of(userE, userC, CONNECTED);
		Link fromCToE = Link.of(userC, userE, CONNECTED);
		Link fromEToB = Link.of(userE, userB, CONNECTED);
		Link fromBToE = Link.of(userB, userE, CONNECTED);
		linkRepository.saveAll(
			List.of(fromAToB, fromBToA, fromAToC, fromCToA, fromBToC, fromCToB, fromBToD, fromDToB, fromEToC, fromCToE,
				fromEToB, fromBToE));

		//when
		List<Link> twoHops = linkRepository.findTwoHops(userA.getId());

		//then
		assertThat(twoHops.size()).isEqualTo(3);
		assertThat(twoHops).containsExactlyInAnyOrder(fromBToD, fromBToE, fromCToE);
	}
}