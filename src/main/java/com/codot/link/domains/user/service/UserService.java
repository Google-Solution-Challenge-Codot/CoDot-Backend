package com.codot.link.domains.user.service;

import static com.codot.link.common.exception.model.ErrorCode.*;
import static com.codot.link.domains.link.domain.Status.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.link.domain.ConnectionPoint;
import com.codot.link.domains.link.domain.Link;
import com.codot.link.domains.link.repository.LinkRepository;
import com.codot.link.domains.user.dao.OneHopDirectSearchResult;
import com.codot.link.domains.user.dao.ThreeHopDirectSearchResult;
import com.codot.link.domains.user.dao.TwoHopDirectSearchResult;
import com.codot.link.domains.user.domain.User;
import com.codot.link.domains.user.dto.request.FriendSearchRequest;
import com.codot.link.domains.user.dto.request.UserDeleteRequest;
import com.codot.link.domains.user.dto.request.UserSignupRequest;
import com.codot.link.domains.user.dto.request.UserUpdateRequest;
import com.codot.link.domains.user.dto.response.TwoHopListResponse;
import com.codot.link.domains.user.dto.response.TwoHopResponse;
import com.codot.link.domains.user.dto.response.UserInfoResponse;
import com.codot.link.domains.user.dto.response.direct.DirectSearchResponse;
import com.codot.link.domains.user.dto.response.direct.FirstHopResponse;
import com.codot.link.domains.user.dto.response.direct.SecondHopResponse;
import com.codot.link.domains.user.dto.response.direct.ThirdHopResponse;
import com.codot.link.domains.user.dto.response.friend.FriendSearchListResponse;
import com.codot.link.domains.user.dto.response.friend.FriendSearchResponse;
import com.codot.link.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final LinkRepository linkRepository;

	public void userSignup(UserSignupRequest request) {
		validateNicknameDuplicate(request.getNickname());
		userRepository.save(User.from(request));
	}

	public UserInfoResponse userInfo(Long userId) {
		User user = findOne(userId);
		return UserInfoResponse.from(user);
	}

	public void userUpdate(Long userId, UserUpdateRequest request) {
		User user = findOne(userId);

		if (request.getNickname() != null) {
			validateNicknameDuplicate(request.getNickname());
		}
		user.updateInfo(request);
	}

	public void userDelete(Long userId, UserDeleteRequest request) {
		User user = findOne(userId);
		confirmUserByEmail(user, request.getEmail());
		userRepository.delete(user);
	}

	public User findOne(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> CustomException.from(USER_NOT_FOUND));
	}

	private void confirmUserByEmail(User user, String email) {
		if (!user.getEmail().equals(email)) {
			throw CustomException.from(EMAIL_NOT_MATCH);
		}
	}

	private void validateNicknameDuplicate(String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			throw CustomException.from(DUPLICATE_USER_NICKNAME);
		}
	}

	public TwoHopListResponse userHome(Long userId) {
		List<Link> links = linkRepository.findTwoHops(userId);
		Map<Long, Link> distinctByToId = new HashMap<>();

		links.forEach(link -> distinctByToId.put(link.getTo().getId(), link));
		Collection<Link> distinctLinks = distinctByToId.values();

		List<TwoHopResponse> twoHops = distinctLinks.stream()
			.map(TwoHopResponse::from)
			.toList();
		return TwoHopListResponse.from(twoHops);
	}

	public DirectSearchResponse userDirectSearch(Long userId, String nickname) {
		DirectSearchResponse result = searchFirstHop(userId, nickname);
		if (result != null) {
			return result;
		}

		result = searchSecondHop(userId, nickname);
		if (result != null) {
			return result;
		}

		return searchThirdHop(userId, nickname);

	}

	private DirectSearchResponse searchFirstHop(Long userId, String nickname) {
		Optional<OneHopDirectSearchResult> firstHopOptional = userRepository.findUserByDirectSearchFirstHop(
			userId, nickname);
		if (firstHopOptional.isEmpty()) {
			return null;
		}

		OneHopDirectSearchResult result = firstHopOptional.get();
		User firstHop = findOne(result.getFirstHopId());

		FirstHopResponse firstHopResponse = FirstHopResponse.of(firstHop, result.getSourceFirstCp());
		return DirectSearchResponse.builder()
			.firstHop(firstHopResponse)
			.build();
	}

	private DirectSearchResponse searchSecondHop(Long userId, String nickname) {
		Optional<TwoHopDirectSearchResult> twoHopOptional = userRepository.findUserByDirectSearchTwoHop(
			userId, nickname);
		if (twoHopOptional.isEmpty()) {
			return null;
		}

		TwoHopDirectSearchResult result = twoHopOptional.get();
		User firstHop = findOne(result.getFirstHopId());
		User secondHop = findOne(result.getSecondHopId());

		FirstHopResponse firstHopResponse = FirstHopResponse.of(firstHop, result.getSourceFirstCp());
		SecondHopResponse secondHopResponse = SecondHopResponse.of(secondHop, result.getFirstSecondCp());
		return DirectSearchResponse.builder()
			.firstHop(firstHopResponse)
			.secondHop(secondHopResponse)
			.build();
	}

	private DirectSearchResponse searchThirdHop(Long userId, String nickname) {
		Optional<ThreeHopDirectSearchResult> threeHopOptional = userRepository.findUserByDirectSearchThreeHop(
			userId, nickname);
		if (threeHopOptional.isEmpty()) {
			return DirectSearchResponse.builder().build();
		}

		ThreeHopDirectSearchResult result = threeHopOptional.get();
		User firstHop = findOne(result.getFirstHopId());
		User secondHop = findOne(result.getSecondHopId());
		User thirdHop = findOne(result.getThirdHopId());

		FirstHopResponse firstHopResponse = FirstHopResponse.of(firstHop, result.getSourceFirstCp());
		SecondHopResponse secondHopResponse = SecondHopResponse.of(secondHop, result.getFirstSecondCp());
		ThirdHopResponse thirdHopResponse = ThirdHopResponse.of(thirdHop, result.getSecondThirdCp());
		return DirectSearchResponse.builder()
			.firstHop(firstHopResponse)
			.secondHop(secondHopResponse)
			.thirdHop(thirdHopResponse)
			.build();
	}

	public FriendSearchListResponse userFriendSearch(FriendSearchRequest request) {
		List<User> users = checkPreviousHopsAndGetFriends(request.getChosenUserId(), request.getPreviousHops());
		Map<String, ConnectionPoint> connectionPoints = checkPreviousHopsAndGetConnectionPoints(
			request.getChosenUserId(), request.getPreviousHops());

		List<FriendSearchResponse> friends = users.stream()
			.map(FriendSearchResponse::from)
			.toList();
		return FriendSearchListResponse.of(friends, connectionPoints.get("sourceTarget"),
			connectionPoints.get("targetSource"));
	}

	private List<User> checkPreviousHopsAndGetFriends(Long chosenUserId, List<Long> previousHops) {
		if (previousHops.isEmpty()) {
			return userRepository.findMyFriends(chosenUserId);
		}
		return userRepository.findMyFriendsByFriendSearch(chosenUserId, previousHops);
	}

	private Map<String, ConnectionPoint> checkPreviousHopsAndGetConnectionPoints(Long chosenUserId,
		List<Long> previousHops) {
		ConnectionPoint sourceTarget = null;
		ConnectionPoint targetSource = null;
		Map<String, ConnectionPoint> connectionPoints = new HashMap<>();

		if (!previousHops.isEmpty()) {
			Long previousHopId = previousHops.get(previousHops.size() - 1);

			sourceTarget = findLinkByFromIdAndToId(previousHopId, chosenUserId).getConnectionPoint();
			targetSource = findLinkByFromIdAndToId(chosenUserId, previousHopId).getConnectionPoint();
		}

		connectionPoints.put("sourceTarget", sourceTarget);
		connectionPoints.put("targetSource", targetSource);
		return connectionPoints;
	}

	private Link findLinkByFromIdAndToId(Long fromId, Long toId) {
		return linkRepository.findByFrom_IdAndTo_IdAndStatus(fromId, toId, CONNECTED)
			.orElseThrow(() -> CustomException.from(LINK_NOT_FOUND));
	}
}
