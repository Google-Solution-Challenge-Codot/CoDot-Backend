package com.codot.link.domains.fcm.service.usecase;

import org.springframework.stereotype.Component;

import com.codot.link.domains.fcm.dto.request.NotificationRequest;
import com.codot.link.domains.fcm.utils.FcmUtils;
import com.codot.link.domains.group.domain.Group;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyGroupUserCase {

	private final FcmUtils fcmUtils;
	private final FirebaseMessaging firebaseMessaging;

	public void execute(NotificationRequest request, Group group) {
		MulticastMessage message = fcmUtils.createPushMessageForGroupUsers(request, group);
		try {
			firebaseMessaging.sendEachForMulticast(message);
			log.info("successfully sent push notification to all users in group id = {}", group.getId());
		} catch (FirebaseMessagingException exception) {
			log.info("failed to send push notification to users in group id = {}", group.getId());
		}
	}
}
