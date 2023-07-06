package com.marzhiievskyi.home_notes.domain.api.common;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagResponseRowMapper implements RowMapper<TagResponseDto> {
    @Override
    public TagResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TagResponseDto.builder()
                .tagId(rs.getLong("id"))
                .text(rs.getString("text"))
                .build();
    }
}
