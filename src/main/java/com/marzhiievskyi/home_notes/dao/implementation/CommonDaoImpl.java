package com.marzhiievskyi.home_notes.dao.implementation;

import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.domain.api.common.TagResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.TagResponseRowMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
