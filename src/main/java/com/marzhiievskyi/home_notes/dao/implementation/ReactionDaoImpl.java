package com.marzhiievskyi.home_notes.dao.implementation;

import com.marzhiievskyi.home_notes.dao.ReactionDao;
import com.marzhiievskyi.home_notes.domain.api.common.UserResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.UserResponseRowMapper;
import com.marzhiievskyi.home_notes.domain.api.communication.comment.WhoseCommentDto;
import com.marzhiievskyi.home_notes.domain.api.communication.comment.WhoseCommentRowMapper;
import com.marzhiievskyi.home_notes.domain.api.communication.comment.CommentNoteRequestDto;
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
public class ReactionDaoImpl extends JdbcDaoSupport implements ReactionDao {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }


    @Override
    public void likeNote(Long userId, long noteId) {
        jdbcTemplate.update("INSERT IGNORE INTO like_note(note_id, user_id) VALUES (?, ?)", noteId, userId);
    }

    @Override
    public void deleteLike(Long userId, long noteId) {
        jdbcTemplate.update("DELETE FROM like_note WHERE note_id = ? AND  user_id = ?", noteId, userId);
    }

    @Override
    public void commentNote(Long userId, CommentNoteRequestDto commentNoteRequest) {
        jdbcTemplate.update("INSERT IGNORE INTO comment(user_id, note_id, text) VALUES (?, ?, ?)", userId, commentNoteRequest.getNoteId(), commentNoteRequest.getText());
    }

    @Override
    public WhoseCommentDto checkWhoseComment(Long commentId) {
        try {
            return jdbcTemplate.queryForObject("SELECT comment.user_id AS comment_user_id, n.user_id AS note_user_id " +
                    "FROM comment " +
                    "           JOIN note n  ON n.id = comment.note_id " +
                    "WHERE  comment.id = ?", new WhoseCommentRowMapper(), commentId);
        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.getMessage());
            throw CommonException.builder()
                    .code(Code.COMMENT_NOT_FOUND)
                    .userMessage("comment not found")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        jdbcTemplate.update("DELETE FROM comment WHERE comment.id = ?", commentId);
    }

    @Override
    public void blockUser(Long userId, Long blockUserId) {
        jdbcTemplate.update("INSERT IGNORE block(user_id, block_user_id) VALUES (?, ?)", userId, blockUserId);
    }

    @Override
    public List<UserResponseDto> getMyBlockedUsers(Long userId) {
        return jdbcTemplate.query("SELECT id, nickname FROM user WHERE id IN (SELECT block_user_id FROM  block WHERE  user_id = ?)", new UserResponseRowMapper(), userId);
    }

    @Override
    public void unblockUser(Long userId, Long unblockUserId) {
        jdbcTemplate.update("DELETE FROM block WHERE user_id = ? AND block_user_id = ?", userId, unblockUserId);
    }
}
