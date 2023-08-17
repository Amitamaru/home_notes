package com.marzhiievskyi.home_notes.controller.service;

import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.domain.api.common.UserDto;
import com.marzhiievskyi.home_notes.util.EncryptProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.marzhiievskyi.home_notes.controller.userController.UserTestData.*;

@Service
public class CommonTestService {
    @Autowired
    CommonDao commonDao;
    @Autowired
    UserDao userDao;
    @Autowired
    EncryptProcessor encryptProcessor;

    public void tearDown() {
        userDao.removeUser(USER_NICKNAME);
        commonDao.removeNotesByText(NOTE_TEXT);
        commonDao.removeNotesByText(NOTE2_TEXT);
    }

    public void prepareUser() {
        userDao.insertNewUser(UserDto.builder()
                .nickname(USER_NICKNAME)
                .encryptedPassword(encryptProcessor.encryptPassword(USER_PASSWORD))
                .accessToken(USER_ACCESS_TOKEN)
                .build());
    }
}
