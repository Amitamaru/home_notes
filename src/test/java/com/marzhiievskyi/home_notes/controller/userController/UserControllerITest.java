package com.marzhiievskyi.home_notes.controller.userController;

import com.marzhiievskyi.home_notes.AbstractControllerTest;
import com.marzhiievskyi.home_notes.controller.service.CommonTestService;
import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.marzhiievskyi.home_notes.controller.userController.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerITest extends AbstractControllerTest {
    //URLS
    private static final String REST_URL = "/home-notes";
    private static final String REST_USER_URL = REST_URL + "/user";
    private static final String REST_USER_REGISTRATION_URL = REST_USER_URL + "/registration";
    private static final String REST_USER_LOGIN_URL = REST_USER_URL + "/login";
    private static final String REST_USER_PUBLIC_NOTE_URL = REST_USER_URL + "/publicNote";
    private static final String REST_USER_GET_MY_NOTES_URL = REST_USER_URL + "/getMyNotes";
    private static final String REST_USER_CHANGE_LOGIN_PASSWORD_URL = REST_USER_URL + "/changeAuthorization";


    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    CommonDao commonDao;
    @Autowired
    CommonTestService commonTestService;


    @AfterEach
    void tearDown() {
        commonTestService.clearData();
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
                .content(USER_AMITAMARU_JSON);
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
        commonTestService.prepareUser();
        var requestBuilder = MockMvcRequestBuilders.post(REST_USER_REGISTRATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(USER_AMITAMARU_JSON);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().is4xxClientError(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(NICKNAME_BUSY_ERROR_JSON)
                );
    }


    @Test
    public void loginUser_payloadsValid_returnsValidResponse() throws Exception {
        //given
        commonTestService.prepareUser();
        var requestBuilder = MockMvcRequestBuilders.post(REST_USER_LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(USER_AMITAMARU_JSON);
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
        commonTestService.prepareUser();
        var requestBuilder = MockMvcRequestBuilders.post(REST_USER_LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(USER_AMITAMARU_WRONG_DATA_JSON);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().is4xxClientError(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(USER_NOT_FOUND_ERROR_JSON)
                );
    }

    @Test
    public void publicNote_payloadsValid() throws Exception {
        //given
        commonTestService.prepareUser();
        var requestBuilder = MockMvcRequestBuilders.post(REST_USER_PUBLIC_NOTE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header(ACCESS_TOKEN_FIELD, USER_ACCESS_TOKEN)
                .content(USER_PUBLIC_NOTE_JSON);
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
        commonTestService.prepareUser();
        var requestBuilder = MockMvcRequestBuilders.post(REST_USER_PUBLIC_NOTE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header(ACCESS_TOKEN_FIELD, USER_WRONG_ACCESS_TOKEN)
                .content(USER_PUBLIC_NOTE_JSON);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().is4xxClientError(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(AUTHORIZATION_ERROR_JSON)
                );
    }

    @Test
    public void getMyNotes_returnsValidResponse() throws Exception {
        //given
        commonTestService.prepareUser();
        prepareNotes();
        var requestBuilder = MockMvcRequestBuilders.get(REST_USER_GET_MY_NOTES_URL)
                .header(ACCESS_TOKEN_FIELD, USER_ACCESS_TOKEN);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(USER_GET_MY_NOTES_RESPONSE_JSON)
                );
    }

    @Test
    public void changeNicknamePassword_payloadsValid_returnsValidEntityResponse() throws Exception {
        //given
        commonTestService.prepareUser();
        var requestBuilder = MockMvcRequestBuilders.patch(REST_USER_CHANGE_LOGIN_PASSWORD_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header(ACCESS_TOKEN_FIELD, USER_ACCESS_TOKEN)
                .content(USER_TO_CHANGE_LOGIN_PASSWORD_JSON);
        //when
        perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.data.accessToken").exists()
                );
        assertEquals(USER_TO_CHANGE_NICKNAME, commonDao.findUserNicknameByGiven(USER_TO_CHANGE_NICKNAME));
        assertNotEquals(USER_ACCESS_TOKEN, commonDao.findUserAccessTokenByNickname(USER_TO_CHANGE_NICKNAME));
    }
}