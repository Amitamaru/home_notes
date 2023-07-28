package com.marzhiievskyi.home_notes.dao.implementation;

import com.marzhiievskyi.home_notes.dao.SubscriptionDao;
import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.error.exception.CommonException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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
public class SubscriptionDaoImpl extends JdbcDaoSupport implements SubscriptionDao {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }
    @Override
    public void subscription(Long subscriberUserId, Long publisherUserId) {

        try {
            jdbcTemplate.update("INSERT INTO subscription (sub_user_id, pub_user_id) VALUES (?, ?)", subscriberUserId, publisherUserId);
        } catch (DuplicateKeyException e) {
            log.error(e.toString());
            throw CommonException.builder()
                    .code(Code.ALREADY_SUBSCRIBED)
                    .userMessage("you have already subscribed to this user")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        } catch (DataIntegrityViolationException e) {
            log.error(e.toString());
            throw CommonException.builder()
                    .code(Code.PUBLISHER_NOT_FOUND)
                    .userMessage("publisher not found")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
