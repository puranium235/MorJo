package com.morjo.model.service;

import org.springframework.stereotype.Service;

import com.morjo.model.dao.UserDao;
import com.morjo.model.dto.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserDao userDao;

    public boolean checkUser(Long userId) {
        User user = userDao.selectUserByUserId(userId);

        return user != null;
    }

    public int join(User user) {
        User findUser = userDao.selectUserByKakaoId(user.getKakaoId());

        if (findUser != null) {
            return -2;
        }

        String nickname = user.getNickname();
        String pattern = "^[가-힣a-zA-Z0-9]+$";
        if (!nickname.matches(pattern)) {
            return -3;
        }

        return userDao.insertUser(user) == 1 ? 1 : -1;
    }

    public long getUserByKakaoId(Long kakaoId) {
        User user = userDao.selectUserByKakaoId(kakaoId);

        if (user == null) {
            return -1;
        }

        return user.getUserId();
    }
}
