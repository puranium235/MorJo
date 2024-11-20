package com.morjo.model.service;

import com.morjo.model.dao.UserDao;
import com.morjo.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSerivce {

    private final UserDao userDao;

    public boolean join(User user) {
        // !TODO nickname 유효성 검사
        // !TODO 다양한 예외 상황들을 어떻게 http 응답에 보낼까
        return userDao.insertUser(user) == 1;
    }

    public long getUserByKakaoId(Long kakaoId) {
        User user = userDao.selectUser(kakaoId);

        if (user == null) {
            return -1;
        }

        return user.getUserId();
    }
}
