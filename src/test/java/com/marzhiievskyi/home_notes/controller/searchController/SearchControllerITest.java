package com.marzhiievskyi.home_notes.controller.searchController;

import com.marzhiievskyi.home_notes.AbstractControllerTest;
import com.marzhiievskyi.home_notes.controller.testUtils.testData.CommonTestData;
import com.marzhiievskyi.home_notes.controller.testUtils.CommonTestService;
import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.marzhiievskyi.home_notes.controller.testUtils.testData.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SearchControllerITest extends AbstractControllerTest {

    private static final String REST_SEARCH_URL = CommonTestData.REST_URL + "/search";
    private static final String REST_SEARCH_USER_URL = REST_SEARCH_URL + "/searchUser";


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

    @Test
    public void searchUser_payloadsValid_returnsValidResponseEntity() throws Exception {
        //given
        commonTestService.prepareUser(USER_NICKNAME, USER_PASSWORD, USER_ACCESS_TOKEN);
        commonTestService.prepareUser(USER_DESMONT_NICKNAME, USER_DESMONT_PASSWORD_PASSWORD, USER_DESMONT_ACCESS_TOKEN);
        var requestBuilder = MockMvcRequestBuilders.post(REST_SEARCH_USER_URL)
                .header(ACCESS_TOKEN_FIELD, USER_ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "partNickname": "desmont"
                        }
                        """);
        //when
        perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "data": {
                                        "users": [
                                            {
                                                "nickname": "desmont"
                                            }
                                        ]
                                    }
                                }
                                """)
                        );
        assertEquals(USER_DESMONT_NICKNAME, commonDao.findUserNicknameByGiven(USER_DESMONT_NICKNAME));
    }
}
