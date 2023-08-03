package com.marzhiievskyi.home_notes.controller.communication;

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

    @GetMapping("/likeNote/{noteId}")
    public ResponseEntity<Response> likeNote(
            @RequestHeader final String accessToken,
            @PathVariable long noteId) {
        return reactionService.likeNote(accessToken, noteId);
    }

    @GetMapping("/disLikeNote/{noteId}")
    public ResponseEntity<Response> disLikeNote(
            @RequestHeader final String accessToken,
            @PathVariable long noteId) {
        return reactionService.disLikeNote(accessToken, noteId);
    }

}
