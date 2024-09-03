package com.sparta.newsfeed.user.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Post {

    // 유저와의 일대다 양방향
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
