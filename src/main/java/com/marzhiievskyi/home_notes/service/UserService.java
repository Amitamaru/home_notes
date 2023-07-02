package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.domain.api.LoginRequestUserDto;
import com.marzhiievskyi.home_notes.domain.api.LoginResponseUserDto;
import com.marzhiievskyi.home_notes.domain.api.RegistrationRequestUserDto;
import com.marzhiievskyi.home_notes.domain.api.RegistrationResponseUserDto;
import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.model.User;
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

    private final UserDao userDao;


    public ResponseEntity<Response> registration(RegistrationRequestUserDto registerRequest) {

        validationUtils.validationRequest(registerRequest);

        String nickname = registerRequest.getAuthorization().getNickname();

        checkNicknameUserIfExist(nickname);

        String accessToken = UUID.randomUUID()
                .toString()
                .replace("-", "") + System.currentTimeMillis();
        String encryptedPassword = encryptUtils.encryptPassword(registerRequest.getAuthorization().getPassword());
        userDao.save(User.builder()
                .nickname(nickname)
                .accessToken(accessToken)
                .password(encryptedPassword)
                .build());
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(RegistrationResponseUserDto.builder()
                        .accessToken(accessToken)
                        .build())
                .build(), HttpStatus.OK);
    }

    private void checkNicknameUserIfExist(String nickname) {
        if (userDao.existsUserByNicknameLike(nickname)) {
            throw CommonException.builder()
                    .code(Code.NICKNAME_BUSY)
                    .message("This nickname is busy, please enter another")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    public ResponseEntity<Response> login(LoginRequestUserDto loginRequest) {

        validationUtils.validationRequest(loginRequest);

        String encryptedPassword = encryptUtils.encryptPassword(loginRequest.getAuthorization().getPassword());

        String accessToken = userDao.getAccessToken(loginRequest.getAuthorization().getNickname(), encryptedPassword);

        if (accessToken != null) {
            return new ResponseEntity<>(SuccessResponse.builder()
                    .data(LoginResponseUserDto.builder()
                            .accessToken(accessToken)
                            .build())
                    .build(), HttpStatus.OK);
        } else {
            throw CommonException.builder()
                    .code(Code.USER_NOT_FOUND)
                    .message("user not found")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
