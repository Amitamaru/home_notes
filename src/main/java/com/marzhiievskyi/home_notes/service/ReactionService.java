package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.dao.ReactionDao;
import com.marzhiievskyi.home_notes.domain.api.communication.comment.CommentNoteRequestDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import com.marzhiievskyi.home_notes.util.ValidationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionDao reactionDao;
    private final CommonDao commonDao;
    private final ValidationProcessor validationProcessor;

    public ResponseEntity<Response> likeNote(String accessToken, long noteId) {

        validationProcessor.validationDecimalMin("noteId", noteId, 1);
        Long userId = commonDao.findUserIdIByTokenOrThrowException(accessToken);

        reactionDao.likeNote(userId, noteId);
        return new ResponseEntity<>(SuccessResponse.builder()
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> disLikeNote(String accessToken, long noteId) {

        validationProcessor.validationDecimalMin("noteId", noteId, 1);
        Long userId = commonDao.findUserIdIByTokenOrThrowException(accessToken);

        reactionDao.disLikeNote(userId, noteId);
        return new ResponseEntity<>(SuccessResponse.builder()
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> commentNote(String accessToken, CommentNoteRequestDto commentNoteRequest) {

        Long userId = commonDao.findUserIdIByTokenOrThrowException(accessToken);
        validationProcessor.validationRequest(commentNoteRequest);

        reactionDao.commentNote(userId, commentNoteRequest);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> deleteComment(String accessToken, String noteId) {

        return null;
    }
}
