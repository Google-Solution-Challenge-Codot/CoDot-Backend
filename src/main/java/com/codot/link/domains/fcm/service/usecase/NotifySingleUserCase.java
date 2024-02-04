package com.codot.link.domains.fcm.service.usecase;

import org.springframework.stereotype.Component;

import com.codot.link.domains.fcm.dto.request.NotificationRequest;
import com.codot.link.domains.fcm.utils.FcmUtils;
import com.codot.link.domains.user.domain.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifySingleUserCase {

	private final FcmUtils fcmUtils;
	private final FirebaseMessaging firebaseMessaging;

	public void execute(NotificationRequest request, User user) {
		Message message = fcmUtils.createPushMessageForSingleUser(request, user);
		try {
			firebaseMessaging.send(message);
			log.info("successfully sent push notification to user id = {}", user.getId());
		} catch (FirebaseMessagingException exception) {
			log.info("failed to send push notification to user id = {}", user.getId());
		}
	}
}
