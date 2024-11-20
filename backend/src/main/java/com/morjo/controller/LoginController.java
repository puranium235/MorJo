package com.morjo.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morjo.model.dto.KakaoToken;
import com.morjo.model.dto.KakaoTokenInfo;
import com.morjo.model.service.OAuthService;
import com.morjo.model.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final OAuthService oAuthService;
    private final UserService userService;

    @Value("${morjo.client-url}")
    private String CLIENT_URL;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String code, HttpSession session) {
        KakaoToken token = oAuthService.getKakaoToken(code);
        KakaoTokenInfo tokenInfo = oAuthService.getKakaoTokenInfo(token.getAccess_token());

        long userId = userService.getUserByKakaoId(tokenInfo.getId());

        HttpHeaders headers = new HttpHeaders();

        if (userId > 0) {
            session.setAttribute("userId", userId);

            headers.setLocation(URI.create(CLIENT_URL));
            return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).headers(headers).build();
        }

        session.setAttribute("kakaoId", tokenInfo.getId());

        headers.setLocation(URI.create(CLIENT_URL + "/join"));
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).headers(headers).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 상태가 아닙니다");
        }

        session.removeAttribute("userId");
        return ResponseEntity.status(HttpStatus.OK).body("로그인 완료");
    }
}
