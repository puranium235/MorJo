package com.morjo.model.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.morjo.model.dto.GetTokenResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthService {

	public GetTokenResponse getToken(String code) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=utf-8"));
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", "91ec8246a07f95b20ae07bcdac56c362"); // 키를 숨겨라
		body.add("redirect_uri", "https://morjo.com/login/kakao/code");
		body.add("code", code);
		
		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);
		
		GetTokenResponse response = restTemplate.postForEntity("https://kauth.kakao.com/oauth/token", entity, GetTokenResponse.class).getBody();
		
		return response;
	}
	
}
