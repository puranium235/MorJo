package com.morjo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morjo.model.dto.User;
import com.morjo.model.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody User user, HttpSession session) {
        Long kakaoId = (Long) session.getAttribute("kakaoId");

        if (kakaoId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("가입중인 유저가 아닙니다");
        }

        user.setKakaoId(kakaoId);
        boolean success = userService.join(user);

        // !TODO 따로 에러를 줘야하는 상황 : 닉네임 중복
        if (success) {
            session.removeAttribute("kakaoId");
            session.setAttribute("userId", user.getUserId());
            ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 닉네임입니다");
    }

    @GetMapping("/")
    public ResponseEntity<?> getUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 상태가 아닙니다");
        }

        // !TODO UserService.getUserByUserId() 만들어지면 주석 풀기
        boolean check = userService.checkUser(userId);

        if (!check) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사이트 회원이 아닙니다");
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
