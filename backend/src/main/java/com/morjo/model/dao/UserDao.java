package com.morjo.model.dao;

import com.morjo.model.dto.User;

public interface UserDao {
    User selectUser(Long kakaoId);
    int insertUser(User user);
}
