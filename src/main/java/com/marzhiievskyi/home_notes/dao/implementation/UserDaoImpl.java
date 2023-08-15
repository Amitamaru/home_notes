package com.marzhiievskyi.home_notes.dao.implementation;

import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseRowMapper;
import com.marzhiievskyi.home_notes.domain.api.common.UserDto;
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
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {


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
                    .userMessage("user not found")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }


    @Override
    public Long findUserIdIOrThrowException(Long userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT id from user WHERE id = ?", Long.class, userId);

        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.toString());
            throw CommonException.builder()
                    .code(Code.USER_NOT_FOUND)
                    .userMessage("user not found")
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
    public List<NoteResponseDto> getNotesByUserId(Long userId) {
        return jdbcTemplate.query(  "SELECT note.id AS note_id, u.id AS user_id, u.nickname, note.text, note.time_insert " +
                                        "FROM note JOIN user u on note.user_id = u.id " +
                                        "WHERE user_id = ? " +
                                        "ORDER BY time_insert DESC ", new NoteResponseRowMapper(), userId);
    }

    public void removeUser(String nickname) {
        jdbcTemplate.update("DELETE FROM user WHERE nickname = ?", nickname);
    }
}
