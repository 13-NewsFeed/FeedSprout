package com.sparta.newsfeed.comment.dto;

import lombok.Getter;

@Getter
public class CommentSaveRequestDto {
    private String contents;
    private Long userId;
}


