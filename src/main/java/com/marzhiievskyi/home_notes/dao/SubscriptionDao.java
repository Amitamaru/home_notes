package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionDao {
    void subscription(Long subscriberUserId, Long publisherUserId);

    void unsubscription(Long subscriberUserId, Long publisherUserId);

    List<UserResponseDto> getUserSubscribers(Long userIdI);

    List<UserResponseDto> getUserPublishers(Long userId);

    List<NoteResponseDto> findMyPublishersNotes(Long userId, int from, int limit);
}
