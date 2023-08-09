package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.api.common.UserResponseDto;
import com.marzhiievskyi.home_notes.domain.api.communication.comment.WhoseCommentDto;
import com.marzhiievskyi.home_notes.domain.api.communication.comment.CommentNoteRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReactionDao {
    void likeNote(Long userId, long noteId);
    void disLikeNote(Long userId, long noteId);

    void commentNote(Long userId, CommentNoteRequestDto commentNoteRequest);

    WhoseCommentDto checkWhoseComment(Long commentId);

    void deleteComment(Long commentId);

    void blockUser(Long userId, Long blockUserId);

    List<UserResponseDto> getMyBlockedUsers(Long userId);

    void unblockUser(Long userId, Long unblockUserId);
}
