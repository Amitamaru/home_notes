package com.marzhiievskyi.home_notes.controller.communication;

import com.marzhiievskyi.home_notes.domain.api.communication.comment.CommentNoteRequestDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/deleteLike/{noteId}")
    public ResponseEntity<Response> deleteLike(
            @RequestHeader final String accessToken,
            @PathVariable long noteId) {
        return reactionService.deleteLike(accessToken, noteId);
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

    @DeleteMapping("/blockUser/{blockUserId}")
    public ResponseEntity<Response> blockUser(
            @RequestHeader final String accessToken,
            @PathVariable Long blockUserId) {
        return reactionService.blockUser(accessToken, blockUserId);
    }

    @GetMapping("/getBlockedUsers")
    public ResponseEntity<Response> getBlockedUsers(@RequestHeader final String accessToken) {
        return reactionService.getMyBlockedUsers(accessToken);
    }
    @DeleteMapping("/unblockUser/{unblockUserId}")
    public ResponseEntity<Response> unblockUser(
            @RequestHeader final String accessToken,
            @PathVariable Long unblockUserId) {
        return reactionService.unblockUser(accessToken, unblockUserId);
    }

}
