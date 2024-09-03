package com.sparta.newsfeed.post.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostRequestDto {

    private String title;
    private String contents;
    private String image;

}