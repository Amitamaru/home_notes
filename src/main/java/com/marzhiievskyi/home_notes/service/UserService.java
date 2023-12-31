package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.dao.implementation.UserDaoImpl;
import com.marzhiievskyi.home_notes.domain.api.common.NoteListResponse;
import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.UserDto;
import com.marzhiievskyi.home_notes.domain.api.user.common.UserTokenResponseDto;
import com.marzhiievskyi.home_notes.domain.api.user.login.LoginRequestUserDto;
import com.marzhiievskyi.home_notes.domain.api.user.publicnote.PublicNoteRequestDto;
import com.marzhiievskyi.home_notes.domain.api.user.registration.RegistrationRequestUserDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import com.marzhiievskyi.home_notes.service.common.CommonService;
import com.marzhiievskyi.home_notes.util.EncryptProcessor;
import com.marzhiievskyi.home_notes.util.ValidationProcessor;
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

    private final ValidationProcessor validationProcessor;
    private final EncryptProcessor encryptProcessor;
    private final UserDaoImpl userDao;
    private final CommonService commonService;
    private final CommonDao commonDao;


    public ResponseEntity<Response> registration(RegistrationRequestUserDto registerRequest) {

        validationProcessor.validationRequest(registerRequest);

        String nickname = registerRequest.getAuthorization().getNickname();
        commonService.checkUserNickname(nickname);

        String accessToken = encryptProcessor.generateAccessToken();
        String encryptedPassword = encryptProcessor.encryptPassword(registerRequest.getAuthorization().getPassword());

        userDao.insertNewUser(UserDto.builder()
                .nickname(nickname)
                .accessToken(accessToken)
                .encryptedPassword(encryptedPassword)
                .build());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(UserTokenResponseDto.builder()
                        .accessToken(accessToken)
                        .build())
                .build(), HttpStatus.OK);
    }


    public ResponseEntity<Response> login(LoginRequestUserDto loginRequest) {

        validationProcessor.validationRequest(loginRequest);

        String encryptedPassword = encryptProcessor.encryptPassword(loginRequest.getAuthorization().getPassword());
        String accessToken = userDao.getAccessTokenIfExist(UserDto.builder()
                .nickname(loginRequest.getAuthorization().getNickname())
                .encryptedPassword(encryptedPassword)
                .build());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(UserTokenResponseDto.builder()
                        .accessToken(accessToken)
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> publicNote(PublicNoteRequestDto publicRequestNote, String accessToken) {

        validationProcessor.validationRequest(publicRequestNote);

        Long userId = commonDao.findUserIdIByTokenOrThrowException(accessToken);
        Long noteId = userDao.addNoteByUserId(publicRequestNote.getText(), userId);

        for (String tag : publicRequestNote.getTags()) {
            userDao.addTag(tag);
            userDao.addNoteTag(noteId, tag);
        }

        return new ResponseEntity<>(SuccessResponse.builder()
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> getUserNotes(String accessToken) {

        Long userId = commonDao.findUserIdIByTokenOrThrowException(accessToken);
        List<NoteResponseDto> notesByUserId = userDao.getNotesByUserId(userId);

        commonService.insertDataIntoNotes(notesByUserId);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(NoteListResponse.builder()
                        .notes(notesByUserId)
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> changeLoginAndPassword(String accessToken, LoginRequestUserDto changeRequest) {

        Long userId = commonDao.findUserIdIByTokenOrThrowException(accessToken);
        validationProcessor.validationRequest(changeRequest);

        String newUserPassword = encryptProcessor.encryptPassword(changeRequest.getAuthorization().getPassword());
        String newUserNickname = changeRequest.getAuthorization().getNickname();
        String oldNickname = commonDao.findUserNicknameById(userId);

        if (!oldNickname.equals(newUserNickname)) {
            commonService.checkUserNickname(newUserNickname);
        }

        String newUserAccessToken = encryptProcessor.generateAccessToken();
        String encryptedPassword = encryptProcessor.encryptPassword(newUserPassword);

        userDao.updateUser(userId, UserDto.builder()
                .nickname(newUserNickname)
                .encryptedPassword(encryptedPassword)
                .accessToken(newUserAccessToken)
                .build());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(UserTokenResponseDto.builder()
                        .accessToken(newUserAccessToken)
                        .build())
                .build(), HttpStatus.OK);
    }
}
