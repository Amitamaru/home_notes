package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.api.user.UserDto;
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

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class DaoImpl extends JdbcDaoSupport implements Dao {


    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }


    @Override
    public Boolean isExistNickname(String nickname) {
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT * FROM user WHERE nickname = ?);",
                Integer.class,
                nickname) != 0;
    }

    @Override
    public void insertNewUser(UserDto userDto) {
        jdbcTemplate.update("INSERT INTO user(nickname, password, access_token) VALUES (?, ?, ?)",
                userDto.getNickname(),
                userDto.getEncryptedPassword(),
                userDto.getAccessToken());
    }

    @Override
    public String getAccessTokenIfExist(UserDto userDto) {
        try {
            return jdbcTemplate.queryForObject("SELECT access_token FROM user WHERE nickname = ? AND password = ?",
                    String.class,
                    userDto.getNickname(),
                    userDto.getEncryptedPassword());
        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.toString());
            throw CommonException.builder()
                    .code(Code.USER_NOT_FOUND)
                    .message("user not found")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }


    }


}
