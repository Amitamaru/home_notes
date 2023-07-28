package com.marzhiievskyi.home_notes.controller.communication;

import com.marzhiievskyi.home_notes.domain.api.communication.subscription.SubscriptionRequestDto;
import com.marzhiievskyi.home_notes.domain.api.communication.subscription.UnsubscriptionRequestDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home-notes/communication/")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscription")
    public ResponseEntity<Response> subscription(
            @RequestHeader final String accessToken,
            @RequestBody final SubscriptionRequestDto subscriptionRequestDto) {
        return subscriptionService.subscription(subscriptionRequestDto, accessToken);
    }

    @PostMapping("/unsubscription")
    public ResponseEntity<Response> unsubscription(
            @RequestHeader final String accessToken,
            @RequestBody final UnsubscriptionRequestDto unsubscriptionRequestDto) {
        return subscriptionService.unsubscription(unsubscriptionRequestDto, accessToken);
    }

    @GetMapping("/getMySubscribers")
    public ResponseEntity<Response> getMySubscribers(
            @RequestHeader final String accessToken) {
        return subscriptionService.getMySubscribers(accessToken);
    }

    @GetMapping("/getMyPublisher")
    public ResponseEntity<Response> getMyPublisher(
            @RequestHeader final String accessToken) {
        return subscriptionService.getMyPublishers(accessToken);
    }
}
