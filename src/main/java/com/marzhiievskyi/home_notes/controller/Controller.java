package com.marzhiievskyi.home_notes.controller;

import com.marzhiievskyi.home_notes.domain.api.RegistrationRequestUserDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.service.HomeNotesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/registration")
    public ResponseEntity<Response> testMethod(@RequestBody final RegistrationRequestUserDto registerRequest) {
        log.info("START endpoint registration, request: {}", registerRequest);
        ResponseEntity<Response> response = homeNotesService.registration(registerRequest);
        log.info("END endpoint registration, response: {}", response);
        return response;
    }
}
