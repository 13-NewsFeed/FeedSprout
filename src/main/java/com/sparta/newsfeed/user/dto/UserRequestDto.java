package com.sparta.newsfeed.user.dto;


import lombok.Getter;


@Getter
public class UserRequestDto {

    private String email;
    private String password;
    private String nickname;
    private String role;

}
