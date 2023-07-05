package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.api.note.NoteDto;
import com.marzhiievskyi.home_notes.domain.api.user.UserDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface Dao {

    Boolean isExistNickname(String nickname);

    void insertNewUser(UserDto userDto);

    String getAccessTokenIfExist(UserDto userDto);

    Long getUserIdByAccessToken(String token);

    Long addNoteByUserId(String noteText, Long userId);

    void addTag(String tagText);

    void addNoteTag(Long noteId, String tag);

    LocalDateTime getTimeInsertNote(Long noteId);

    List<NoteDto> getNotesByUserId(Long userId);

    List<String> getTagsByNoteId(Long noteId);
}
