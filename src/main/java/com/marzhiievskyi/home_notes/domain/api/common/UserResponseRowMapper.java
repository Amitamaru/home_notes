package com.marzhiievskyi.home_notes.domain.api.common;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResponseRowMapper implements RowMapper<UserResponseDto> {
    @Override
    public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserResponseDto.builder()
                .userId(rs.getLong("id"))
                .nickname(rs.getString("nickname"))
                .build();
    }
}
