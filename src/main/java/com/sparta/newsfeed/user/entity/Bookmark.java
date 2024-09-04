package com.sparta.newsfeed.user.entity;

import jakarta.persistence.*;

@Entity
public class Bookmark {
    @Id
    @GeneratedValue
    private Long id;


/*    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/
}
