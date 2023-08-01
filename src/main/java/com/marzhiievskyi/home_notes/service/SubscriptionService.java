package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.SubscriptionDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.domain.api.common.NoteListResponse;
import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.UserResponseDto;
import com.marzhiievskyi.home_notes.domain.api.communication.subscribers.MySubscribersResponseDto;
import com.marzhiievskyi.home_notes.domain.api.communication.subscription.SubscriptionRequestDto;
import com.marzhiievskyi.home_notes.domain.api.communication.subscription.UnsubscriptionRequestDto;
import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import com.marzhiievskyi.home_notes.domain.response.error.exception.CommonException;
import com.marzhiievskyi.home_notes.service.common.CommonService;
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
    private final CommonService commonService;



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

        validationProcessor.validationRequest(unsubscriptionRequestDto);

        Long subscriberUserId = userDao.findUserIdIByTokenOrThrowException(accessToken);
        Long publisherUserId = unsubscriptionRequestDto.getPubUserId();

        subscriptionDao.unsubscription(subscriberUserId, publisherUserId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> getMySubscribers(String accessToken) {

        Long userId = userDao.findUserIdIByTokenOrThrowException(accessToken);
        List<UserResponseDto> userSubscribers = subscriptionDao.getUserSubscribers(userId);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(MySubscribersResponseDto.builder()
                        .subscribers(userSubscribers)
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> getMyPublishers(String accessToken) {

        Long userId = userDao.findUserIdIByTokenOrThrowException(accessToken);
        List<UserResponseDto> userPublishers = subscriptionDao.getUserPublishers(userId);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(MySubscribersResponseDto.builder()
                        .subscribers(userPublishers)
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> findMyPublishersNotes(String accessToken, int from, int limit) {

        validationProcessor.validationDecimalMin("from", from, 0);
        validationProcessor.validationDecimalMin("limit", limit, 1);

        Long userId = userDao.findUserIdIByTokenOrThrowException(accessToken);

        List<NoteResponseDto> myPublishersNotes = subscriptionDao.findMyPublishersNotes(userId, from, limit);
        commonService.insertDataIntoNotes(myPublishersNotes);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(NoteListResponse.builder()
                        .notes(myPublishersNotes)
                        .build())
                .build(), HttpStatus.OK);
    }
}
