package com.codot.link.domains.user.repository;

import static com.codot.link.domains.link.domain.Status.*;
import static com.codot.link.domains.user.domain.GraduationStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.codot.link.domains.link.domain.Link;
import com.codot.link.domains.link.repository.LinkRepository;
import com.codot.link.domains.user.dao.OneHopDirectSearchResult;
import com.codot.link.domains.user.dao.TwoHopDirectSearchResult;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.dto.request.UserSignupRequest;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	LinkRepository linkRepository;

	// === findMyFriends Test === //
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

	// === findUserByDirectSearchFirstHop Test === //
	@Test
	@DisplayName("Direct Search 로직에서 first hop에 있는 사용자를 닉네임으로 조회할 수 있어야 한다")
	void Direct_Search_로직에서_first_hop에_있는_사용자를_닉네임으로_조회할_수_있어야_한다() {
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
		linkRepository.saveAll(
			List.of(fromAToB, fromBToA, fromBToC, fromCToB));

		//when
		OneHopDirectSearchResult targetUser = userRepository.findUserByDirectSearchFirstHop(userA.getId(),
			"nicknameB").orElseThrow(NoSuchElementException::new);

		//then
		assertThat(targetUser.getFirstHopId()).isEqualTo(userB.getId());
	}

	@Test
	@DisplayName("해당 닉네임을 가진 사용자가 존재하더라도 1 hop 내에 없다면 조회하지 않아야 한다")
	void 해당_닉네임을_가진_사용자가_존재하더라도_1_hop_내에_없다면_조회하지_않아야_한다() {
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
		linkRepository.saveAll(
			List.of(fromAToB, fromBToA, fromBToC, fromCToB));

		//when
		Optional<OneHopDirectSearchResult> targetUser = userRepository.findUserByDirectSearchFirstHop(userA.getId(),
			"nicknameC");

		//then
		assertThrows(NoSuchElementException.class, targetUser::get);
	}

	// === findUserByDirectSearchTwoHop Test === //
	@Test
	@DisplayName("Direct Search 로직에서 second hop에 있는 사용자를 닉네임으로 조회할 수 있어야 한다")
	void Direct_Search_로직에서_second_hop에_있는_사용자를_닉네임으로_조회할_수_있어야_한다() {
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
		linkRepository.saveAll(
			List.of(fromAToB, fromBToA, fromBToC, fromCToB));

		//when
		TwoHopDirectSearchResult targetUser = userRepository.findUserByDirectSearchTwoHop(userA.getId(),
			"nicknameC").orElseThrow(NoSuchElementException::new);

		//then
		assertThat(targetUser.getFirstHopId()).isEqualTo(userB.getId());
		assertThat(targetUser.getSecondHopId()).isEqualTo(userC.getId());
	}

	@Test
	@DisplayName("해당 닉네임을 가진 사용자가 존재하더라도 2 hop 에 없다면 조회하지 않아야 한다")
	void 해당_닉네임을_가진_사용자가_존재하더라도_2_hop_에_없다면_조회하지_않아야_한다() {
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
		linkRepository.saveAll(
			List.of(fromAToB, fromBToA, fromBToC, fromCToB));

		//when
		Optional<TwoHopDirectSearchResult> targetUser = userRepository.findUserByDirectSearchTwoHop(userA.getId(),
			"nicknameB");

		//then
		assertThrows(NoSuchElementException.class, targetUser::get);
	}

	@Test
	@DisplayName("해당 닉네임을 가진 사용자와 1 hop 연결도 있고 2 hop 연결도 있다면 해당 사용자를 조회하지 않아야 한다")
	void 해당_닉네임을_가진_사용자와_1_hop_연결도_있고_2_hop_연결도_있다면_해당_사용자를_조회하지_않아야_한다() {
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
		Link fromAToC = Link.of(userA, userC, CONNECTED);
		Link fromCToA = Link.of(userC, userA, CONNECTED);
		linkRepository.saveAll(
			List.of(fromAToB, fromBToA, fromBToC, fromCToB, fromAToC, fromCToA));

		//when
		Optional<TwoHopDirectSearchResult> targetUser = userRepository.findUserByDirectSearchTwoHop(userA.getId(),
			"nicknameC");

		//then
		assertThrows(NoSuchElementException.class, targetUser::get);
	}
}