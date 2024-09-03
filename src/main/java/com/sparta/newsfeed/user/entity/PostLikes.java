package com.sparta.newsfeed.user.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class PostLikes {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
