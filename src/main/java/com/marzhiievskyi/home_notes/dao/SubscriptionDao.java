package com.marzhiievskyi.home_notes.dao;

import org.springframework.stereotype.Service;

@Service
public interface SubscriptionDao {
    void subscription(Long subscriberUserId, Long publisherUserId);
}
