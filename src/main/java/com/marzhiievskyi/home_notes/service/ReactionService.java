package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.dao.ReactionDao;
import com.marzhiievskyi.home_notes.dao.SubscriptionDao;
import com.marzhiievskyi.home_notes.domain.api.common.UserResponseDto;
import com.marzhiievskyi.home_notes.domain.api.communication.blocked.BlockedUsersResponseDto;
import com.marzhiievskyi.home_notes.domain.api.communication.comment.WhoseCommentDto;
import com.marzhiievskyi.home_notes.domain.api.communication.comment.CommentNoteRequestDto;
import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import com.marzhiievskyi.home_notes.domain.response.error.Error;
import com.marzhiievskyi.home_notes.domain.response.error.ErrorResponse;
import com.marzhiievskyi.home_notes.common.CommonService;
import com.marzhiievskyi.home_notes.util.ValidationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionDao reactionDao;
    private final CommonDao commonDao;
    private final SubscriptionDao subscriptionDao;
    private final ValidationProcessor validationProcessor;
    private final CommonService commonService;

    public ResponseEntity<Response> likeNote(String accessToken, long noteId) {

        validationProcessor.validationDecimalMin("noteId", noteId, 1);
        Long userId = commonDao.findUserIdIByTokenOrThrowException(accessToken);

        commonService.checkBlockUserByNoteId(userId, noteId);

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

        commonService.checkBlockUserByNoteId(userId, commentNoteRequest.getNoteId());

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

    public ResponseEntity<Response> blockUser(String accessToken, Long blockUserId) {

        Long userId = commonDao.findUserIdIByTokenOrThrowException(accessToken);
        validationProcessor.validationDecimalMin("blockUserId", blockUserId, 1);

        if (userId.equals(blockUserId)) {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .error(Error.builder()
                            .code(Code.NOT_BLOCK_YOURSELF)
                            .userMessage("you can not block your self")
                            .build())
                    .build(), HttpStatus.BAD_REQUEST);
        }

        reactionDao.blockUser(userId, blockUserId);
        subscriptionDao.unsubscription(userId, blockUserId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> getMyBlockedUsers(String accessToken) {

        Long userId = commonDao.findUserIdIByTokenOrThrowException(accessToken);

        List<UserResponseDto> blockedUsers = reactionDao.getMyBlockedUsers(userId);
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(BlockedUsersResponseDto.builder()
                        .blockedUsers(blockedUsers)
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> unblockUser(String accessToken, Long unblockUserId) {

        validationProcessor.validationDecimalMin("unblockUserId", unblockUserId, 1);
        Long userId = commonDao.findUserIdIByTokenOrThrowException(accessToken);

        reactionDao.unblockUser(userId, unblockUserId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }
}
