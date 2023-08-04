package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.dao.ReactionDao;
import com.marzhiievskyi.home_notes.domain.api.communication.comment.WhoseCommentDto;
import com.marzhiievskyi.home_notes.domain.api.communication.comment.CommentNoteRequestDto;
import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import com.marzhiievskyi.home_notes.domain.response.error.Error;
import com.marzhiievskyi.home_notes.domain.response.error.ErrorResponse;
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

    public ResponseEntity<Response> deleteComment(String accessToken, Long commentId) {

        Long userId = commonDao.findUserIdIByTokenOrThrowException(accessToken);
        validationProcessor.validationDecimalMin("noteId", commentId, 1);

        WhoseCommentDto whoseCommentDto = reactionDao.checkWhoseComment(commentId);
        if (whoseCommentDto.getCommentUserId().equals(userId) || whoseCommentDto.getNoteUserId().equals(userId)) {
            reactionDao.deleteComment(commentId);
            return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .error(Error.builder()
                            .code(Code.NOT_YOUR_COMMENT)
                            .userMessage("this is not your comment or not comment of your note")
                            .build())
                    .build(), HttpStatus.BAD_REQUEST);
        }

    }
}
