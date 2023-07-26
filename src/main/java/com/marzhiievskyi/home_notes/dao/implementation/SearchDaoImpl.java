package com.marzhiievskyi.home_notes.dao.implementation;

import com.marzhiievskyi.home_notes.dao.SearchDao;
import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseRowMapper;
import com.marzhiievskyi.home_notes.domain.api.common.TagResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.TagResponseRowMapper;
import com.marzhiievskyi.home_notes.domain.api.search.note.SearchNoteByTagRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.note.SearchNotesByWordRequestDto;
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
public class SearchDaoImpl extends JdbcDaoSupport implements SearchDao {

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
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TagResponseDto> getTagsByTagPart(String partTag) {
        return jdbcTemplate.query("SELECT id, text " +
                        "FROM ( SELECT tag.id, text, count(tag.id) " +
                        "       FROM tag " +
                        "           JOIN note_tag nt on tag.id = nt.tag_id " +
                        "       WHERE text LIKE CONCAT (LOWER(?), '%') " +
                        "       GROUP BY tag.id " +
                        "       ORDER BY count(tag.id) DESC) t1 " +
                        "UNION " +
                        "SELECT id, text " +
                        "FROM (" +
                        "       SELECT tag.id, text, count(tag.id) " +
                        "       FROM tag " +
                        "           JOIN note_tag n on tag.id = n.tag_id " +
                        "       WHERE text LIKE CONCAT('%_', LOWER(?), '%') " +
                        "       GROUP BY tag.id " +
                        "       ORDER BY count(tag.id) DESC ) t2",
                new TagResponseRowMapper(), partTag, partTag);
    }

    @Override
    public List<NoteResponseDto> getNotesByTag(SearchNoteByTagRequestDto searchNoteByTagRequestDto) {
        return jdbcTemplate.query("SELECT note.id AS note_id, u.id AS user_id, u.nickname, note.text, note.time_insert " +
                "FROM note " +
                "       JOIN user u on note.user_id = u.id " +
                "WHERE note.id IN (SELECT note_id FROM note_tag WHERE tag_id = ?) " +
                "ORDER BY " + searchNoteByTagRequestDto.getSort().getValue() + ";", new NoteResponseRowMapper(), searchNoteByTagRequestDto.getTagId());
    }

    @Override
    public List<NoteResponseDto> findNotesByPartWord(SearchNotesByWordRequestDto searchNotesByWordRequestDto) {
        return jdbcTemplate.query("SELECT note.id AS note_id, u.id AS user_id, u.nickname, note.text, note.time_insert " +
                "FROM note " +
                "       JOIN user u on note.user_id = u.id " +
                "WHERE note.text LIKE CONCAT('%', ?, '%') " +
                "ORDER BY " + searchNotesByWordRequestDto.getSort().getValue() + ";", new NoteResponseRowMapper(), searchNotesByWordRequestDto.getPartWord());
    }
}
