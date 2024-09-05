package com.sparta.newsfeed.user.dto;

import com.sparta.newsfeed.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private Long id;
    private String password;
    private String email;
    private String username;
    private String profileImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public UserResponseDto(User user) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
        this.profileImageUrl = user.getProfileImage().getImageUrl();
    }
}
