package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.SubscriptionDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.domain.api.common.UserResponseDto;
import com.marzhiievskyi.home_notes.domain.api.communication.subscription.SubscriptionRequestDto;
import com.marzhiievskyi.home_notes.domain.api.communication.subscription.UnsubscriptionRequestDto;
import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import com.marzhiievskyi.home_notes.domain.response.error.exception.CommonException;
import com.marzhiievskyi.home_notes.util.ValidationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final ValidationProcessor validationProcessor;
    private final UserDao userDao;
    private final SubscriptionDao subscriptionDao;


    public ResponseEntity<Response> subscription(SubscriptionRequestDto subscriptionRequestDto, String accessToken) {

        validationProcessor.validationRequest(subscriptionRequestDto);

        Long subscriberUserId = userDao.findUserIdIByTokenOrThrowException(accessToken);
        Long publisherUserId = subscriptionRequestDto.getPubUserId();

        if (Objects.equals(subscriberUserId, publisherUserId)) {
            throw CommonException.builder()
                    .code(Code.SUBSCRIPTION_LOGIC_ERROR)
                    .userMessage("you can not subscribe by your self")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        subscriptionDao.subscription(subscriberUserId, publisherUserId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> unsubscription(UnsubscriptionRequestDto unsubscriptionRequestDto, String accessToken) {

        return null;
    }

    public ResponseEntity<Response> getMySubscribers(String accessToken) {

        userDao.findUserIdIByTokenOrThrowException(accessToken);

        return null;
    }

    public ResponseEntity<Response> getMyPublishers(String accessToken) {

        userDao.findUserIdIByTokenOrThrowException(accessToken);



        return null;
    }
}
