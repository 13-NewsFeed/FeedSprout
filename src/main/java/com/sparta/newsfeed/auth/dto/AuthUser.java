package com.sparta.newsfeed.auth.dto;

import lombok.Getter;

@Getter
public class AuthUser {
    private final Long id;

    public AuthUser(final Long id) {
        this.id = id;
    }
}
