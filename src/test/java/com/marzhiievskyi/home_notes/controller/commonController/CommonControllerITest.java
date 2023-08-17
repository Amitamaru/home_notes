package com.marzhiievskyi.home_notes.controller.commonController;

import com.marzhiievskyi.home_notes.AbstractControllerTest;
import com.marzhiievskyi.home_notes.controller.service.CommonTestService;
import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.domain.api.common.UserDto;
import com.marzhiievskyi.home_notes.service.SearchService;
import com.marzhiievskyi.home_notes.util.EncryptProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.marzhiievskyi.home_notes.controller.userController.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CommonControllerITest extends AbstractControllerTest {

    private static final String REST_URL = "/home-notes";
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
        userDao.insertNewUser(UserDto.builder()
                .nickname(USER_NICKNAME)
                .encryptedPassword(encryptProcessor.encryptPassword(USER_PASSWORD))
                .accessToken(USER_ACCESS_TOKEN)
                .build());
        userDao.addNoteByUserId(NOTE_TEXT, commonDao.findUserIdIByTokenOrThrowException(USER_ACCESS_TOKEN));
        userDao.addNoteByUserId(NOTE2_TEXT, commonDao.findUserIdIByTokenOrThrowException(USER_ACCESS_TOKEN));

    }

    @AfterEach
    public void tearDown() {
        commonTestService.tearDown();
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
                        content().json("""
                                {
                                    "data": {
                                        "notes": [
                                            {            \s
                                                "nickname": "amitamaru",
                                                "text": "second note test",
                                                "likesCount": 0
                                            },
                                            {             \s
                                                "nickname": "amitamaru",
                                                "text": "note test",
                                                "likesCount": 0
                                            }
                                        ]
                                    }
                                }
                                """)
                );

    }
}