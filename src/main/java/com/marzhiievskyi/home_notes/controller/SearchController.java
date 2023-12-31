package com.marzhiievskyi.home_notes.controller;

import com.marzhiievskyi.home_notes.domain.api.search.note.SearchNoteByTagRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.note.SearchNoteByUserRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.note.SearchNotesByWordRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.tag.SearchTagsRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.user.SearchUserByNicknameRequestDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/searchTags")
    public ResponseEntity<Response> searchTags(
            @RequestHeader final String accessToken,
            @RequestBody final SearchTagsRequestDto searchTagRequest) {
        return searchService.findTagsByPart(searchTagRequest, accessToken);
    }

    @PostMapping("/searchNotesByTag")
    public ResponseEntity<Response> searchNotesByTag(
            @RequestHeader final String accessToken,
            @RequestBody final SearchNoteByTagRequestDto searchNoteByTagRequestDto) {
        return searchService.findNotesByTag(searchNoteByTagRequestDto, accessToken);
    }

    @PostMapping("/searchNotesByPartWord")
    public ResponseEntity<Response> searchNotesByPartWord(
            @RequestHeader final String accessToken,
            @RequestBody final SearchNotesByWordRequestDto searchNotesByWordRequestDto) {
        return searchService.findNotesByPartWord(searchNotesByWordRequestDto, accessToken);
    }

    @PostMapping("/searchUser")
    public ResponseEntity<Response> searchUserByNicknamePart(
            @RequestHeader final String accessToken,
            @RequestBody final SearchUserByNicknameRequestDto searchUserByNicknameRequestDto) {
        return searchService.findUserByPartNickname(searchUserByNicknameRequestDto, accessToken);
    }

    @PostMapping("/searchNotesByUser")
    public ResponseEntity<Response> searchNotesByUser(
            @RequestHeader final String accessToken,
            @RequestBody final SearchNoteByUserRequestDto searchNoteByUserRequestDto) {
        return searchService.findNotesByUser(searchNoteByUserRequestDto, accessToken);
    }
}
