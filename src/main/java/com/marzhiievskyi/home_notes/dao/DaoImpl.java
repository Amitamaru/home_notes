package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.api.common.NoteRowMapper;
import com.marzhiievskyi.home_notes.domain.api.note.NoteDto;
import com.marzhiievskyi.home_notes.domain.api.user.UserDto;
import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.error.exception.CommonException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class DaoImpl extends JdbcDaoSupport implements Dao {


    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }


    @Override
    public Boolean isExistNickname(String nickname) {
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT * FROM user WHERE nickname = ?);",
                Integer.class,
                nickname) != 0;
    }

    @Override
    public void insertNewUser(UserDto userDto) {
        jdbcTemplate.update("INSERT INTO user(nickname, password, access_token) VALUES (?, ?, ?)",
                userDto.getNickname(),
                userDto.getEncryptedPassword(),
                userDto.getAccessToken());
    }

    @Override
    public String getAccessTokenIfExist(UserDto userDto) {
        try {
            return jdbcTemplate.queryForObject("SELECT access_token FROM user WHERE nickname = ? AND password = ?",
                    String.class,
                    userDto.getNickname(),
                    userDto.getEncryptedPassword());
        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.toString());
            throw CommonException.builder()
                    .code(Code.USER_NOT_FOUND)
                    .message("user not found")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public Long getUserIdByAccessToken(String token) {
        try {
            return jdbcTemplate.queryForObject("SELECT id from user where access_token = ?", Long.class, token);

        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.toString());
            throw CommonException.builder()
                    .code(Code.AUTHORIZATION_ERROR)
                    .message("error of authorization")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public Long addNoteByUserId(String noteText, Long userId) {
        jdbcTemplate.update("INSERT INTO note (user_id, text) VALUES (?, ?);", userId, noteText);
        return jdbcTemplate.queryForObject("SELECT id FROM note WHERE id = LAST_INSERT_ID();", Long.class);
    }

    @Override
    public void addTag(String tagText) {
        jdbcTemplate.update("INSERT INTO tag(text) SELECT DISTINCT LOWER(?) FROM tag WHERE NOT EXISTS (SELECT text FROM tag WHERE text = LOWER(?));", tagText, tagText);
    }

    @Override
    public void addNoteTag(Long noteId, String tag) {
        jdbcTemplate.update("INSERT IGNORE INTO note_tag(note_id, tag_id) VALUES (?, (SELECT id FROM tag WHERE text = LOWER(?)));", noteId, tag);
    }

    @Override
    public LocalDateTime getTimeInsertNote(Long noteId) {
        return jdbcTemplate.queryForObject("SELECT time_insert FROM note where id = ?;", LocalDateTime.class, noteId);
    }

    @Override
    public List<NoteDto> getNotesByUserId(Long userId) {
        return jdbcTemplate.query("SElECT * FROM note WHERE user_id = ? ORDER BY time_insert DESC", new NoteRowMapper(), userId);
    }

    @Override
    public List<String> getTagsByNoteId(Long noteId) {
        return jdbcTemplate.queryForList("SELECT text FROM tag WHERE id IN (SELECT tag_id FROM note_tag WHERE note_id = ?)", String.class, noteId);
    }
}
