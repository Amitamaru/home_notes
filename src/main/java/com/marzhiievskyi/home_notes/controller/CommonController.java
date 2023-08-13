package com.marzhiievskyi.home_notes.controller;

import com.marzhiievskyi.home_notes.domain.constants.ConstantsMessages;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home-notes")
public class CommonController {

    private final SearchService searchService;

    @GetMapping("/version")
    public String version() {
        return ConstantsMessages.HOME_NOTE + ConstantsMessages.VERSION + ConstantsMessages.RELEASE;
    }
    @GetMapping("/getNotes/{from}/{limit}")
    public ResponseEntity<Response> getLatestNotes(
            @PathVariable int from,
            @PathVariable int limit) {
        return searchService.findNotesFromToLimit(from, limit);
    }
}
