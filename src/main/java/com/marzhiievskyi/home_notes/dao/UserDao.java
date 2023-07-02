package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long>{

    @Query("select (count(u) > 0) from User u where u.nickname like :nickname")
    Boolean existsUserByNicknameLike(@Param("nickname") String nickname);

    @Query("select u.accessToken from User u where u.nickname = :nickname and u.password = :password")
    String getAccessToken(@Param("nickname") String nickname, @Param("password") String password);
}
