package com.marzhiievskyi.home_notes.controller.testUtils;

import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.domain.api.common.UserDto;
import com.marzhiievskyi.home_notes.util.EncryptProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.marzhiievskyi.home_notes.controller.testUtils.testData.UserTestData.*;

@Service
public class CommonTestService {
    @Autowired
    CommonDao commonDao;
    @Autowired
    UserDao userDao;
    @Autowired
    EncryptProcessor encryptProcessor;

    public void clearData() {
        userDao.removeUser(USER_NICKNAME);
        userDao.removeUser(USER_DESMONT_NICKNAME);
        commonDao.removeNotesByText(NOTE_TEXT);
        commonDao.removeNotesByText(NOTE2_TEXT);
    }

    public void prepareUser(String nickname, String userPassword, String accessToken) {
        userDao.insertNewUser(UserDto.builder()
                .nickname(nickname)
                .encryptedPassword(encryptProcessor.encryptPassword(userPassword))
                .accessToken(accessToken)
                .build());
    }
}
