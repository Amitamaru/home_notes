package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.DaoImpl;
import com.marzhiievskyi.home_notes.domain.api.note.PublicRequestNoteDto;
import com.marzhiievskyi.home_notes.domain.api.user.LoginRequestUserDto;
import com.marzhiievskyi.home_notes.domain.api.user.RegistrationRequestUserDto;
import com.marzhiievskyi.home_notes.domain.api.user.RegistrationResponseUserDto;
import com.marzhiievskyi.home_notes.domain.api.user.UserDto;
import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import com.marzhiievskyi.home_notes.domain.response.error.exception.CommonException;
import com.marzhiievskyi.home_notes.util.EncryptUtils;
import com.marzhiievskyi.home_notes.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ValidationUtils validationUtils;
    private final EncryptUtils encryptUtils;
    private final DaoImpl dao;

    public ResponseEntity<Response> registration(RegistrationRequestUserDto registerRequest) {

        validationUtils.validationRequest(registerRequest);

        String nickname = registerRequest.getAuthorization().getNickname();

        if (dao.isExistNickname(nickname)) {
            throw CommonException.builder()
                    .code(Code.NICKNAME_BUSY)
                    .message("this nickname is busy please enter another")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        String accessToken = UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis();
        String encryptedPassword = encryptUtils.encryptPassword(registerRequest.getAuthorization().getPassword());

        dao.insertNewUser(UserDto.builder()
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

        String accessToken = dao.getAccessTokenIfExist(UserDto.builder()
                .nickname(loginRequest.getAuthorization().getNickname())
                .encryptedPassword(encryptedPassword)
                .build());
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(accessToken)
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> publicNote(PublicRequestNoteDto publicRequestNote, String accessToken) {

        validationUtils.validationRequest(publicRequestNote);

        //TODO public note!
        return null;
    }

}
