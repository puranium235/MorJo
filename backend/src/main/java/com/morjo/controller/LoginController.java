package com.morjo.controller;

import com.morjo.model.dto.KakaoTokenInfo;
import com.morjo.model.dto.User;
import com.morjo.model.service.UserSerivce;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.morjo.model.dto.KakaoToken;
import com.morjo.model.service.OAuthService;

import lombok.RequiredArgsConstructor;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final OAuthService oAuthService;
    private final UserSerivce userSerivce;

    @Value("${morjo.client-url}")
    private String CLIENT_URL;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String code, HttpSession session) {
        KakaoToken token = oAuthService.getKakaoToken(code);
        KakaoTokenInfo tokenInfo = oAuthService.getKakaoTokenInfo(token.getAccess_token());

        long userId = userSerivce.getUserByKakaoId(tokenInfo.getId());

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
