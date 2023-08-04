package com.marzhiievskyi.home_notes.domain.api.communication.comment;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WhoseCommentRowMapper implements RowMapper<WhoseCommentDto> {
    @Override
    public WhoseCommentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return WhoseCommentDto.builder()
                .commentUserId(rs.getLong("comment_user_id"))
                .noteUserId(rs.getLong("note_user_id"))
                .build();
    }
}
