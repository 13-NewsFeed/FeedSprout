package com.sparta.newsfeed.user.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRequestDto {

    private String email;
    private String password;
    private String NewPassword;
    private String NewConfirmPassword;
    private String username;
    private String profileImageUrl;
}
