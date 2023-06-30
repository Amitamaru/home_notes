package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long>{

    Boolean existsUserLikeNickname(String nickname);
}
