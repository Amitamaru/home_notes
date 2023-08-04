package com.marzhiievskyi.home_notes.controller.communication;

import com.marzhiievskyi.home_notes.domain.api.communication.comment.CommentNoteRequestDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.service.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home-notes/reaction/")
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping("/likeNote/{noteId}")
    public ResponseEntity<Response> likeNote(
            @RequestHeader final String accessToken,
            @PathVariable long noteId) {
        return reactionService.likeNote(accessToken, noteId);
    }

    @DeleteMapping("/disLikeNote/{noteId}")
    public ResponseEntity<Response> disLikeNote(
            @RequestHeader final String accessToken,
            @PathVariable long noteId) {
        return reactionService.disLikeNote(accessToken, noteId);
    }

    @PostMapping("/commentNote")
    public ResponseEntity<Response> commentNote(
            @RequestHeader final String accessToken,
            @RequestBody final CommentNoteRequestDto commentNoteRequest) {
        return reactionService.commentNote(accessToken, commentNoteRequest);
    }
    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<Response> deleteComment(
            @RequestHeader final String accessToken,
            @PathVariable Long commentId) {
        return reactionService.deleteComment(accessToken, commentId);
    }
    @PostMapping("/blockUser/{blockUserId}")
    public ResponseEntity<Response> blockUser(
            @RequestHeader final String accessToken,
            @PathVariable Long blockUserId) {
        return reactionService.blockUser(accessToken, blockUserId);
    }


}
