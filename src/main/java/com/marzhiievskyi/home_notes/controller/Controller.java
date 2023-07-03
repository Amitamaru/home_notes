package com.marzhiievskyi.home_notes.controller;

import com.marzhiievskyi.home_notes.domain.api.note.PublicRequestNoteDto;
import com.marzhiievskyi.home_notes.domain.api.user.LoginRequestUserDto;
import com.marzhiievskyi.home_notes.domain.api.user.RegistrationRequestUserDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home-notes")
public class Controller {

    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        String hello = "Welcome to home notes. version 1.0.0";
        log.info(hello);
        return hello;
    }

    @PostMapping("/registration")
    public ResponseEntity<Response> registrationNewUser(@RequestBody final RegistrationRequestUserDto registerRequest) {
        log.info("START endpoint registration, request: {}", registerRequest);
        ResponseEntity<Response> response = userService.registration(registerRequest);
        log.info("END endpoint registration, response: {}", response);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody final LoginRequestUserDto loginRequest) {
        log.info("START endpoint login, request: {}", loginRequest);
        ResponseEntity<Response> response = userService.login(loginRequest);
        log.info("END endpoint login, response: {}", response);
        return response;
    }
    @PostMapping("/publicNote")
    public ResponseEntity<Response> publicNote(
            @RequestHeader final String accessToken,
            @RequestBody final PublicRequestNoteDto publicRequestNote) {
        log.info("START endpoint publicNote, accessToken: {}, request {}", accessToken, publicRequestNote);
        ResponseEntity<Response> response = userService.publicNote(publicRequestNote, accessToken);
        log.info("END endpoint, response: {}", response);
        return response;
    }
}
