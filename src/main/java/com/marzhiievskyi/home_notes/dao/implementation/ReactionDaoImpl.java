package com.marzhiievskyi.home_notes.dao.implementation;

import com.marzhiievskyi.home_notes.dao.ReactionDao;
import com.marzhiievskyi.home_notes.domain.api.communication.comment.CommentNoteRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

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
    public void disLikeNote(Long userId, long noteId) {
        jdbcTemplate.update("DELETE FROM like_note WHERE note_id = ? AND  user_id = ?", noteId, userId);
    }

    @Override
    public void commentNote(Long userId, CommentNoteRequestDto commentNoteRequest) {
        jdbcTemplate.update("INSERT IGNORE INTO comment(user_id, note_id, text) VALUES (?, ?, ?)", userId, commentNoteRequest.getNoteId(), commentNoteRequest.getText());
    }
}
