package com.marzhiievskyi.home_notes.domain.api.common;

import com.marzhiievskyi.home_notes.domain.api.note.NoteDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteRowMapper implements RowMapper<NoteDto> {
    @Override
    public NoteDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return NoteDto.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .text(rs.getString("text"))
                .timeInsert(rs.getString("time_insert"))
                .build();
    }
}
