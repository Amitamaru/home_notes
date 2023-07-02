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
import com.marzhiievskyi.home_notes.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeNotesService {

    private final ValidationUtil validationUtil;

    private final UserDao userDao;


    public ResponseEntity<Response> registration(RegistrationRequestUserDto registerRequest) {

        validationUtil.validationRequest(registerRequest);

        String nickname = registerRequest.getNickname();

        checkNicknameUserIfExist(nickname);

        String accessToken = UUID.randomUUID()
                .toString()
                .replace("-", "") + System.currentTimeMillis();
        String encryptedPassword = DigestUtils.md5DigestAsHex(registerRequest.getPassword().getBytes());
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

        validationUtil.validationRequest(loginRequest);

        String encryptedPassword = DigestUtils.md5DigestAsHex(loginRequest.getPassword().getBytes());

        String accessToken = userDao.getAccessToken(loginRequest.getNickname(), encryptedPassword);

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
