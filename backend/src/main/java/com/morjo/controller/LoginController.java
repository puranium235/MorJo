package com.morjo.controller;

import com.morjo.model.dto.User;
import jakarta.servlet.http.Cookie;
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

    @Value("${morjo.client-url}")
    private String CLIENT_URL;

    @Value("${morjo.server-url}")
    private String SERVER_URL;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String code) {
        KakaoToken token = oAuthService.getKakaoToken(code);
        String accessToken = token.getAccess_token();
        String refreshToken = token.getRefresh_token();

        boolean isUser = oAuthService.isUser(accessToken);

        // !TODO cookie의 sameSite 옵션에 대해 더 알아보고 적용
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 30)
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 30)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", String.valueOf(accessTokenCookie));
        headers.add("Set-Cookie", String.valueOf(refreshTokenCookie));

        if (isUser) {
            headers.setLocation(URI.create(CLIENT_URL + "/login?isValidUser=true"));
            return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).headers(headers).build();
        }

        headers.setLocation(URI.create(CLIENT_URL + "/join"));
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).headers(headers).build();
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody User user, @CookieValue("accessToken") String accessToken) {
        boolean success = oAuthService.join(user, accessToken);

        // !TODO 따로 에러를 줘야하는 상황 : 닉네임 중복, accessToken 없음
        return success ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
