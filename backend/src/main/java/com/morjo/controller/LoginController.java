package com.morjo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morjo.model.dto.GetTokenResponse;
import com.morjo.model.service.OAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class LoginController {
	
	private final OAuthService oAuthService;

	@PostMapping("/token")
	public ResponseEntity<GetTokenResponse> GetToken(@RequestBody String code) {
		GetTokenResponse response = oAuthService.getToken(code);
		return ResponseEntity.ok(response);
	}
	
}
