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
}
