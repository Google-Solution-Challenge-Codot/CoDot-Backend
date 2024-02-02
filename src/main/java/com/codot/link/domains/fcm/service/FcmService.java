package com.codot.link.domains.fcm.service;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FcmService {

	private final FirebaseMessaging firebaseMessaging;
}
