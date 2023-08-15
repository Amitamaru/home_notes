package com.marzhiievskyi.home_notes.controller.userController;

import com.marzhiievskyi.home_notes.AbstractControllerTest;
import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.domain.api.common.UserDto;
import com.marzhiievskyi.home_notes.service.UserService;
import com.marzhiievskyi.home_notes.util.EncryptProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.marzhiievskyi.home_notes.controller.userController.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerITest extends AbstractControllerTest {
    //URLS
    private static final String REST_URL = "/home-notes";
    private static final String REST_USER_URL = REST_URL + "/user";
    private static final String REST_USER_REGISTRATION_URL = REST_USER_URL + "/registration";
    private static final String REST_USER_LOGIN_URL = REST_USER_URL + "/login";
    private static final String REST_USER_PUBLIC_NOTE_URL = REST_USER_URL + "/publicNote";
    private static final String REST_USER_GET_MY_NOTES_URL = REST_USER_URL + "/getMyNotes";


    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    EncryptProcessor encryptProcessor;
    @Autowired
    CommonDao commonDao;



    @AfterEach
    void tearDown() {
        userDao.removeUser(USER_NICKNAME);
        commonDao.removeNotesByText(NOTE_TEXT);
        commonDao.removeNotesByText(NOTE2_TEXT);
    }

    private void prepareUser() {
        userDao.insertNewUser(UserDto.builder()
                .nickname(USER_NICKNAME)
                .encryptedPassword(encryptProcessor.encryptPassword(USER_PASSWORD))
                .accessToken(USER_ACCESS_TOKEN)
                .build());
    }

    private void prepareNotes() {
        userDao.addNoteByUserId(NOTE_TEXT, commonDao.findUserIdIByTokenOrThrowException(USER_ACCESS_TOKEN));
        userDao.addNoteByUserId(NOTE2_TEXT, commonDao.findUserIdIByTokenOrThrowException(USER_ACCESS_TOKEN));
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
        assertEquals(USER_NICKNAME, commonDao.findUserNicknameByGiven(USER_NICKNAME));
    }

    @Test
    public void registrationNewUser_payloadsInvalid_returnsErrorResponseEntity() throws Exception {
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
        assertEquals(USER_NICKNAME, commonDao.findUserNicknameByGiven(USER_NICKNAME));
    }

    @Test
    public void loginUser_payloadsWrongUser_returnsErrorResponseEntity() throws Exception {
        //given
        prepareUser();
        var requestBuilder = MockMvcRequestBuilders.post(REST_USER_LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "authorization": {
                            "nickname": "desmont",
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
                                          "code": "USER_NOT_FOUND",
                                          "userMessage": "user not found"
                                      }
                                  }
                                """)
                );
    }

    @Test
    public void publicNote_payloadsValid() throws Exception {
        //given
        prepareUser();
        var requestBuilder = MockMvcRequestBuilders.post(REST_USER_PUBLIC_NOTE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header("accessToken", USER_ACCESS_TOKEN)
                .content("""
                        {
                          "text": "note test",
                          "tags": [
                                "test",
                                "two",
                                "three"
                          ]
                        }
                        """);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk()
                );
        assertEquals(NOTE_TEXT, commonDao.findNoteTextLikeGiven(NOTE_TEXT));
        assertEquals(TAG_TEXT, commonDao.findTagTextLikeGiven(TAG_TEXT));
    }

    @Test
    public void publicNote_payloadsWrongUser_ReturnsErrorEntity() throws Exception {
        //given
        prepareUser();
        var requestBuilder = MockMvcRequestBuilders.post(REST_USER_PUBLIC_NOTE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header("accessToken", "wrongAccessToken")
                .content("""
                        {
                          "text": "note test",
                          "tags": [
                                "test",
                                "two",
                                "three"
                          ]
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
                                          "code": "AUTHORIZATION_ERROR",
                                          "userMessage": "error of authorization"
                                      }
                                  }
                                """)
                );
    }

    @Test
    public void getMyNotes_returnsValidResponse() throws Exception {
        //given
        prepareUser();
        prepareNotes();
        var requestBuilder = MockMvcRequestBuilders.get(REST_USER_GET_MY_NOTES_URL)
                .header("accessToken", USER_ACCESS_TOKEN);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "data": {
                                        "notes": [
                                            {
                                                "nickname": "amitamaru",
                                                "text": "note test"
                                            },
                                            {
                                                "nickname": "amitamaru",
                                                "text": "second note test"
                                            }
                                        ]
                                    }
                                }
                                """)
                );
    }
}