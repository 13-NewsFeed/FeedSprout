package com.sparta.newsfeed.user.dto;

import com.sparta.newsfeed.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long id;
    private String password;
    private String email;
    private String nickname;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    private String role;


    public UserResponseDto(User user) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.created_at = user.getCreated_at();
        this.modified_at = user.getModified_at();

    }
}
