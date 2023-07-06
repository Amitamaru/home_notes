package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.implementation.UserDaoImpl;
import com.marzhiievskyi.home_notes.domain.api.common.NoteListResponse;
import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseDto;
import com.marzhiievskyi.home_notes.domain.api.user.publicnote.PublicNoteRequestDto;
import com.marzhiievskyi.home_notes.domain.api.user.login.LoginRequestUserDto;
import com.marzhiievskyi.home_notes.domain.api.user.registration.RegistrationRequestUserDto;
import com.marzhiievskyi.home_notes.domain.api.user.registration.RegistrationResponseUserDto;
import com.marzhiievskyi.home_notes.domain.api.common.UserDto;
import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import com.marzhiievskyi.home_notes.domain.response.error.exception.CommonException;
import com.marzhiievskyi.home_notes.util.EncryptUtils;
import com.marzhiievskyi.home_notes.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final ValidationUtils validationUtils;
    private final EncryptUtils encryptUtils;
    private final UserDaoImpl userDao;
    private final SearchService searchService;


    public ResponseEntity<Response> registration(RegistrationRequestUserDto registerRequest) {

        validationUtils.validationRequest(registerRequest);

        String nickname = registerRequest.getAuthorization().getNickname();
        if (userDao.isExistNickname(nickname)) {
            throw CommonException.builder()
                    .code(Code.NICKNAME_BUSY)
                    .message("this nickname is busy please enter another")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        String accessToken = encryptUtils.generateAccessToken();
        String encryptedPassword = encryptUtils.encryptPassword(registerRequest.getAuthorization().getPassword());

        userDao.insertNewUser(UserDto.builder()
                .nickname(nickname)
                .accessToken(accessToken)
                .encryptedPassword(encryptedPassword)
                .build());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(RegistrationResponseUserDto.builder()
                        .accessToken(accessToken)
                        .build())
                .build(), HttpStatus.OK);
    }


    public ResponseEntity<Response> login(LoginRequestUserDto loginRequest) {

        validationUtils.validationRequest(loginRequest);

        String encryptedPassword = encryptUtils.encryptPassword(loginRequest.getAuthorization().getPassword());
        String accessToken = userDao.getAccessTokenIfExist(UserDto.builder()
                .nickname(loginRequest.getAuthorization().getNickname())
                .encryptedPassword(encryptedPassword)
                .build());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(accessToken)
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> publicNote(PublicNoteRequestDto publicRequestNote, String accessToken) {

        validationUtils.validationRequest(publicRequestNote);

        Long userId = userDao.getUserIdByAccessToken(accessToken);
        Long noteId = userDao.addNoteByUserId(publicRequestNote.getText(), userId);
        log.info("userId: {}, noteId: {}", userId, noteId);

        for (String tag : publicRequestNote.getTags()) {
            userDao.addTag(tag);
            userDao.addNoteTag(noteId, tag);
        }

        return new ResponseEntity<>(SuccessResponse.builder()
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> getUserNotes(String accessToken) {

        Long userId = userDao.getUserIdByAccessToken(accessToken);
        List<NoteResponseDto> notesByUserId = userDao.getNotesByUserId(userId);

        for (NoteResponseDto note : notesByUserId) {
            note.setTags(searchService.getTagsByNoteId(note.getNoteId()));
        }

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(NoteListResponse.builder()
                        .notes(notesByUserId)
                        .build())
                .build(), HttpStatus.OK);
    }
}
