package com.morjo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoToken {

	private String access_token;
	private Integer expires_in;
	private String refresh_token;
	private Integer refresh_token_expires_in;

}
