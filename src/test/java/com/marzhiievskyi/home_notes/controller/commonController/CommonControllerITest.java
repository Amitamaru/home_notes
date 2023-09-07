package com.marzhiievskyi.home_notes.controller.commonController;

import com.marzhiievskyi.home_notes.AbstractControllerTest;
import com.marzhiievskyi.home_notes.controller.testUtils.CommonTestService;
import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.service.SearchService;
import com.marzhiievskyi.home_notes.util.EncryptProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.marzhiievskyi.home_notes.controller.testUtils.testData.CommonTestData.*;
import static com.marzhiievskyi.home_notes.controller.testUtils.testData.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CommonControllerITest extends AbstractControllerTest {

    private static final String REST_URL = "/api";
    private static final String REST_URL_VERSION = REST_URL + "/version";
    private static final String REST_URL_GET_LATEST_NOTES = REST_URL + "/getNotes/0/2";

    @Autowired
    SearchService searchService;
    @Autowired
    CommonDao commonDao;
    @Autowired
    UserDao userDao;
    @Autowired
    EncryptProcessor encryptProcessor;
    @Autowired
    CommonTestService commonTestService;

    public void prepareData() {
        commonTestService.prepareUser(USER_NICKNAME, USER_PASSWORD, USER_ACCESS_TOKEN);
        userDao.addNoteByUserId(NOTE_TEXT, commonDao.findUserIdIByTokenOrThrowException(USER_ACCESS_TOKEN));
        userDao.addNoteByUserId(NOTE2_TEXT, commonDao.findUserIdIByTokenOrThrowException(USER_ACCESS_TOKEN));

    }

    @AfterEach
    public void tearDown() {
        commonTestService.clearData();
    }

    @Test
    public void getVersion() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get(REST_URL_VERSION);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    public void getLatestNotes() throws Exception {
        //given
        prepareData();
        var requestBuilder = MockMvcRequestBuilders.get(REST_URL_GET_LATEST_NOTES);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(COMMON_GET_NOTES_RESPONSE_JSON)
                );

    }
}