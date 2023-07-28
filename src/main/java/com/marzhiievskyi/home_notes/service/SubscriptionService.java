package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.domain.api.communication.subscription.SubscriptionRequestDto;
import com.marzhiievskyi.home_notes.domain.api.communication.subscription.UnsubscriptionRequestDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {
    public ResponseEntity<Response> subscription(SubscriptionRequestDto subscriptionRequestDto, String accessToken) {

        return null;
    }

    public ResponseEntity<Response> unsubscription(UnsubscriptionRequestDto unsubscriptionRequestDto, String accessToken) {

        return null;
    }

    public ResponseEntity<Response> getMySubscribers(String accessToken) {
        return null;
    }

    public ResponseEntity<Response> getMyPublisher(String accessToken) {
        return null;
    }
}
