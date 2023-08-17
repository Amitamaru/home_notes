package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDao {

    Boolean isExistNickname(String nickname);

    void insertNewUser(UserDto userDto);

    String getAccessTokenIfExist(UserDto userDto);

    Long findUserIdIOrThrowException(Long userId);

    Long addNoteByUserId(String noteText, Long userId);

    void addTag(String tagText);

    void addNoteTag(Long noteId, String tag);

    List<NoteResponseDto> getNotesByUserId(Long userId);

    void removeUser(String nickname);

    void updateUser(Long userId, UserDto userDto);
}
