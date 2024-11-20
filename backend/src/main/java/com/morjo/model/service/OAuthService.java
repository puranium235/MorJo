package com.morjo.model.service;

import com.morjo.model.dao.UserDao;
import com.morjo.model.dto.KakaoTokenInfo;
import com.morjo.model.dto.User;
import com.morjo.util.OAuthUtil;
import org.springframework.stereotype.Service;

import com.morjo.model.dto.KakaoToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthService {

	private final UserDao userDao;
	private final OAuthUtil oAuthUtil;

	public KakaoToken getKakaoToken(String code) {
		return oAuthUtil.getKakaoToken(code);
	}

	public boolean isUser(String accessToken) {
		KakaoTokenInfo tokenInfo = oAuthUtil.getKakaoTokenInfo(accessToken);
		User user = userDao.selectUser(tokenInfo.getId());

		return user != null;
	}

	public boolean join(User user, String accessToken) {
		KakaoTokenInfo tokenInfo = oAuthUtil.getKakaoTokenInfo(accessToken);
		user.setKakaoId(tokenInfo.getId());

		// !TODO nickname 유효성 검사
		// !TODO 다양한 예외 상황들을 어떻게 http 응답에 보낼까
		return userDao.insertUser(user) == 1;
	}
}
