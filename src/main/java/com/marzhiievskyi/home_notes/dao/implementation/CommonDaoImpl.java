package com.marzhiievskyi.home_notes.dao.implementation;

import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.domain.api.common.CommentResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.CommentResponseRowMapper;
import com.marzhiievskyi.home_notes.domain.api.common.TagResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.TagResponseRowMapper;
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
public class CommonDaoImpl extends JdbcDaoSupport implements CommonDao {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public Long findUserIdIByTokenOrThrowException(String token) {
        try {
            return jdbcTemplate.queryForObject("SELECT id from user where access_token = ?", Long.class, token);

        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.toString());
            throw CommonException.builder()
                    .code(Code.AUTHORIZATION_ERROR)
                    .userMessage("error of authorization")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public List<CommentResponseDto> getCommentsByNoteId(Long noteId) {
        try {
            return jdbcTemplate.query("SELECT comment.id AS comment_id, user_id, nickname, text, comment.time_insert " +
                    "FROM comment " +
                    "           JOIN user u  ON u.id = comment.user_id " +
                    "WHERE note_id = ? " +
                    "ORDER BY  comment.time_insert DESC;", new CommentResponseRowMapper(), noteId);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return null;
        }
    }

    @Override
    public boolean isBlocked(Long userId, Long checkBlockPublisher) {
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM block WHERE user_id = ? AND block_user_id = ?)", Long.class, checkBlockPublisher, userId) != 0;
    }

    @Override
    public String findUserNicknameById(Long userId) {
        return jdbcTemplate.queryForObject("SELECT nickname FROM user WHERE id = ?", String.class, userId);
    }

    @Override
    public Long getUserIdByNoteId(long noteId) {
        return jdbcTemplate.queryForObject("SELECT user_id FROM note WHERE id = ?", Long.class, noteId);
    }

    @Override
    public String findNoteTextLikeGiven(String text) {
        return jdbcTemplate.queryForObject("SELECT text FROM note WHERE text LIKE ?", String.class, text);
    }

    @Override
    public String findTagTextLikeGiven(String text) {
        return jdbcTemplate.queryForObject("SELECT text FROM tag WHERE text LIKE ?", String.class, text);
    }

    @Override
    public String findUserNicknameByGiven(String nickname) {
        return jdbcTemplate.queryForObject("SELECT nickname FROM user WHERE nickname LIKE ?", String.class, nickname);
    }

    @Override
    public void removeNotesByText(String noteText) {
        jdbcTemplate.update("DELETE FROM note WHERE text LIKE ?", noteText);
    }

    @Override
    public String findUserAccessTokenByNickname(String nickname) {
        return jdbcTemplate.queryForObject("SELECT access_token FROM user WHERE nickname LIKE ?", String.class, nickname);
    }

    @Override
    public List<TagResponseDto> getTagsByNoteId(Long noteId) {
        try {
            return jdbcTemplate.query("SELECT id, text FROM tag WHERE id IN (SELECT tag_id FROM note_tag WHERE  note_id = ?)", new TagResponseRowMapper(), noteId);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return null;
        }
    }

    @Override
    public Long getLikesCountByNoteId(Long noteId) {
        try {
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM like_note WHERE note_id = ?", Long.class, noteId);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return 0L;
        }
    }
}
