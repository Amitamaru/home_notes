package com.marzhiievskyi.home_notes.domain.api.common;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteResponseRowMapper implements RowMapper<NoteResponseDto> {
    @Override
    public NoteResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return NoteResponseDto.builder()
                .noteId(rs.getLong("note_id"))
                .userid(rs.getLong("user_id"))
                .nickname(rs.getString("nickname"))
                .text(rs.getString("text"))
                .timeInsert(rs.getString("time_insert"))
                .build();
    }
}
