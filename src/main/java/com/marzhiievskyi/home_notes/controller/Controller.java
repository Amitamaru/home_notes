package com.marzhiievskyi.home_notes.controller;

import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.service.HomeNotesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home-notes")
public class Controller {

    private final HomeNotesService homeNotesService;

    @GetMapping("/hello")
    public String hello() {
        String hello = "Welcome to home notes. version 1.0.0";
        log.info(hello);
        return hello;
    }

    @PostMapping("/test")
    public ResponseEntity<Response> testMethod() {
        log.info("Start ENDPOINT 'test'");
        ResponseEntity<Response> response = homeNotesService.testMethod();
        log.info("End ENDPOINT 'test" + response.toString());
        return response;
    }
}
