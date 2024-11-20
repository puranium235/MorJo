package com.morjo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoTokenInfo {
    private Long id;
    private int expires_in;
    private int app_id;
}
