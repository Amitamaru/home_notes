package com.marzhiievskyi.home_notes.controller;

import com.marzhiievskyi.home_notes.AbstractControllerTest;
import com.marzhiievskyi.home_notes.dao.SearchDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.domain.api.common.UserDto;
import com.marzhiievskyi.home_notes.service.UserService;
import com.marzhiievskyi.home_notes.util.EncryptProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerIT extends AbstractControllerTest {
    //URLS
    private static final String REST_URL = "/home-notes";
    private static final String REST_USER_URL = REST_URL + "/user";
    private static final String REST_USER_REGISTRATION_URL = REST_USER_URL + "/registration";
    private static final String REST_USER_LOGIN_URL = REST_USER_URL + "/login";


    //Data user tests
    private static final String USER_NICKNAME = "amitamaru";
    private static final String USER_PASSWORD = "1234567890";

    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    EncryptProcessor encryptProcessor;
    @Autowired
    SearchDao searchDao;

    @AfterEach
    void tearDown() {
        userDao.removeUser(USER_NICKNAME);
    }

    private void prepareUser() {
        userDao.insertNewUser(UserDto.builder()
                .nickname(USER_NICKNAME)
                .encryptedPassword(encryptProcessor.encryptPassword(USER_PASSWORD))
                .accessToken(encryptProcessor.generateAccessToken())
                .build());
    }

    @Test
    public void registrationNewUser_payloadsValid_returnsValidResponseEntity() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.post(REST_USER_REGISTRATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "authorization": {
                            "nickname": "amitamaru",
                            "password": "1234567890"
                          }
                        }
                        """);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.data.accessToken").exists()
                );
        assertEquals(USER_NICKNAME, searchDao.getUsersByNicknamePart(USER_NICKNAME).get(0).getNickname());
    }

    @Test
    public void registrationNewUser_PayloadsInvalid_returnsErrorResponseEntity() throws Exception {
        //given
        prepareUser();
        var requestBuilder = MockMvcRequestBuilders.post(REST_USER_REGISTRATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "authorization": {
                            "nickname": "amitamaru",
                            "password": "1234567890"
                          }
                        }
                        """);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().is4xxClientError(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                  {
                                      "error": {
                                          "code": "NICKNAME_BUSY",
                                          "userMessage": "this nickname is busy please enter another"
                                      }
                                  }
                                """)
                );
    }



    @Test
    public void loginUser_payloadsValid_returnsValidResponse() throws Exception {
        //given
        prepareUser();
        var requestBuilder = MockMvcRequestBuilders.post(REST_USER_LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "authorization": {
                            "nickname": "amitamaru",
                            "password": "1234567890"
                          }
                        }
                        """);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.data.accessToken").exists()
                );
        assertEquals(USER_NICKNAME, searchDao.getUsersByNicknamePart(USER_NICKNAME).get(0).getNickname());
    }
}