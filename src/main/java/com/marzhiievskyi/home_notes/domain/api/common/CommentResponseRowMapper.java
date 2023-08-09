package com.marzhiievskyi.home_notes.domain.api.common;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentResponseRowMapper implements RowMapper<CommentResponseDto> {
    @Override
    public CommentResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CommentResponseDto.builder()
                .commentId(rs.getLong("comment_id"))
                .userId(rs.getLong("user_id"))
                .nickname(rs.getString("nickname"))
                .text(rs.getString("text"))
                .timeInsert(rs.getString("time_insert"))
                .build();
    }
}
