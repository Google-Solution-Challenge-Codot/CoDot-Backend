package com.codot.link.domains.fcm.utils;

import static com.codot.link.common.exception.model.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Component;

import com.codot.link.common.exception.model.CustomException;
import com.codot.link.domains.fcm.FcmTokenRepository;
import com.codot.link.domains.fcm.domain.FcmToken;
import com.codot.link.domains.fcm.dto.request.NotificationRequest;
import com.codot.link.domains.group.domain.Group;
import com.codot.link.domains.user.domain.User;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FcmUtils {

	private final FcmTokenRepository fcmTokenRepository;

	public Message createPushMessageForSingleUser(NotificationRequest request, User user) {
		return Message.builder()
			.setNotification(createNotificationFrom((request)))
			.setToken(getFcmTokenByUser(user).getDeviceToken())
			.build();
	}

	public MulticastMessage createPushMessageForGroupUsers(NotificationRequest request, Group group) {
		return MulticastMessage.builder()
			.setNotification(createNotificationFrom(request))
			.addAllTokens(getDeviceTokensOfGroupUsers(group))
			.build();
	}

	private Notification createNotificationFrom(NotificationRequest request) {
		return Notification.builder()
			.setTitle(request.getTitle())
			.setBody(request.getContent())
			.build();
	}

	private FcmToken getFcmTokenByUser(User user) {
		return fcmTokenRepository.findByUser(user)
			.orElseThrow(() -> CustomException.from(FCM_TOKEN_NOT_FOUND));
	}

	private List<String> getDeviceTokensOfGroupUsers(Group group) {
		return fcmTokenRepository.findAllByGroupId(group.getId()).stream()
			.map(FcmToken::getDeviceToken)
			.toList();
	}
}
