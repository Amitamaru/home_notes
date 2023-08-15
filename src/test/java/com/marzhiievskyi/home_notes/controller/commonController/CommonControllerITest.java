package com.marzhiievskyi.home_notes.controller.commonController;

import com.marzhiievskyi.home_notes.AbstractControllerTest;
import com.marzhiievskyi.home_notes.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CommonControllerITest extends AbstractControllerTest {

    @Autowired
    SearchService searchService;

    @Test
    public void getVersion() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/home-notes/version");
        //when
        perform(requestBuilder)
        //then
                .andExpectAll(
                        status().isOk()
                );
    }

    //TODO getNotes method need to test

}