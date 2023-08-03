package com.marzhiievskyi.home_notes.dao;

import org.springframework.stereotype.Service;

@Service
public interface ReactionDao {
    void likeNote(Long userId, long noteId);
    void disLikeNote(Long userId, long noteId);
}
