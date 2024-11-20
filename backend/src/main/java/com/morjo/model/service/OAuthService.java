package com.morjo.model.service;

import com.morjo.model.dao.UserDao;
import com.morjo.model.dto.KakaoTokenInfo;
import com.morjo.model.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.morjo.model.dto.KakaoToken;

import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuthService {

	private final UserDao userDao;

	@Value("${kakao.rest-api-key}")
	private String REST_API_KEY;

	@Value("${kakao.redirect-uri}")
	private String KAKAO_REDIRECT_URI;

	public KakaoToken getKakaoToken(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=utf-8"));

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

		body.add("grant_type", "authorization_code");
		body.add("client_id", REST_API_KEY);
		body.add("redirect_uri", KAKAO_REDIRECT_URI);
		body.add("code", code);

		HttpEntity<Object> entity = new HttpEntity<>(body, headers);
		// !TODO 토큰 값 받아오기 실패시 처리 따로 해주기
		return new RestTemplate().postForEntity("https://kauth.kakao.com/oauth/token", entity, KakaoToken.class).getBody();
	}

	public KakaoTokenInfo getKakaoTokenInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		// !TODO 토큰 정보 받아오기 실패시 처리 따로 해주기
		return new RestTemplate().exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, entity, KakaoTokenInfo.class).getBody();
	}
}
