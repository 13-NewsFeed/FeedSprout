package com.sparta.newsfeed.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {

    private String token;
    private String email;

    public LoginResponseDto(String token, String email) {
        this.token = token;
        this.email = email;
    }
}
