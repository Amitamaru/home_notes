package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.api.user.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface Dao {

    Boolean isExistNickname(String nickname);

    void insertNewUser(UserDto userDto);

    String getAccessTokenIfExist(UserDto userDto);
}
