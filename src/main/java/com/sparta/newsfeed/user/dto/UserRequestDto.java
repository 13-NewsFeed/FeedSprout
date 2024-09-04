package com.sparta.newsfeed.user.dto;


import lombok.Getter;


@Getter
public class UserRequestDto {

    private String email;
    private String password;
    private String NewPassword;
    private String NewConfirmPassword;
    private String username;
    private String role;

}
