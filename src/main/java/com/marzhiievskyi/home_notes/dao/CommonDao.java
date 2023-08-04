package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.api.common.CommentResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.TagResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommonDao {
    List<TagResponseDto> getTagsByNoteId(Long noteId);

    Long getLikesCountByNoteId(Long noteId);

    Long findUserIdIByTokenOrThrowException(String token);

    List<CommentResponseDto> getCommentsByNoteId(Long noteId);

    boolean isBlocked(Long userId, Long checkBlockPublisher);

    Long getUserIdByNoteId(long noteId);
}
