package com.morjo.model.dao;

import com.morjo.model.dto.User;

public interface UserDao {
    User selectUserByKakaoId(Long kakaoId);
    User selectUserByUserId(Long UserId);
    int insertUser(User user);
}
