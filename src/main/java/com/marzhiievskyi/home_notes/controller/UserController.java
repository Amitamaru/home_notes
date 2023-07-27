package com.marzhiievskyi.home_notes.controller;

import com.marzhiievskyi.home_notes.domain.api.user.publicnote.PublicNoteRequestDto;
import com.marzhiievskyi.home_notes.domain.api.user.login.LoginRequestUserDto;
import com.marzhiievskyi.home_notes.domain.api.user.registration.RegistrationRequestUserDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home-notes/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Response> registrationNewUser(@RequestBody final RegistrationRequestUserDto registerRequest) {
        return userService.registration(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody final LoginRequestUserDto loginRequest) {
        return userService.login(loginRequest);
    }
    @PostMapping("/publicNote")
    public ResponseEntity<Response> publicNote(
            @RequestHeader final String accessToken,
            @RequestBody final PublicNoteRequestDto publicRequestNote) {
        return userService.publicNote(publicRequestNote, accessToken);

    }
    @GetMapping("/getMyNotes")
    public ResponseEntity<Response> getUserNotes(@RequestHeader String accessToken) {
        return userService.getUserNotes(accessToken);
    }
}