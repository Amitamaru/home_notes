package com.marzhiievskyi.home_notes.controller;

import com.marzhiievskyi.home_notes.domain.api.user.publicnote.PublicNoteRequestDto;
import com.marzhiievskyi.home_notes.domain.api.user.login.LoginRequestUserDto;
import com.marzhiievskyi.home_notes.domain.api.user.registration.RegistrationRequestUserDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
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
    @PatchMapping("/changeAuthorization")
    public ResponseEntity<Response> changeLoginAndPassword(
            @RequestHeader String accessToken,
            @RequestBody final LoginRequestUserDto changeLogPasRequest) {
        return userService.changeLoginAndPassword(accessToken, changeLogPasRequest);
    }
}
