package com.marzhiievskyi.home_notes.controller;

import com.marzhiievskyi.home_notes.domain.api.search.note.SearchNoteByTagRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.note.SearchNotesByWordRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.tag.SearchTagsRequestDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home-notes/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/searchTags")
    public ResponseEntity<Response> searchTags(
            @RequestHeader final String accessToken,
            @RequestBody final SearchTagsRequestDto searchTagRequest) {
        log.info("START endpoint searchTags, request: {}", searchTagRequest);
        ResponseEntity<Response> response = searchService.findTagsByPart(searchTagRequest, accessToken);
        log.info("END endpoint searchTag, response: {}", response);
        return response;
    }

    @PostMapping("/searchNotesByTag")
    public ResponseEntity<Response> searchNotesByTag(
            @RequestHeader final String accessToken,
            @RequestBody final SearchNoteByTagRequestDto searchNoteByTagRequestDto) {
        log.info("START endpoint searchNotesByTag, request: {}", searchNoteByTagRequestDto);
        ResponseEntity<Response> response = searchService.findNotesByTag(searchNoteByTagRequestDto, accessToken);
        log.info("END endpoint searchNotesByTag, response: {}", response);
        return response;
    }

    @PostMapping("/searchNotesByPartWord")
    public ResponseEntity<Response> searchNotesByPartWord(
            @RequestHeader final String accessToken,
            @RequestBody final SearchNotesByWordRequestDto searchNotesByWordRequestDto) {
        log.info("START endpoint searchNotesByPartWord, request: {}", searchNotesByWordRequestDto);
        ResponseEntity<Response> response = searchService.findNotesByPartWord(searchNotesByWordRequestDto, accessToken);
        log.info("END endpoint searchNotesByPartWord, response: {}", response);
        return response;
    }
}
