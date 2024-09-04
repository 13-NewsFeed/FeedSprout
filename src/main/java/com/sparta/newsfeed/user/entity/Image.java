package com.sparta.newsfeed.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    private String imageUrl;
}
