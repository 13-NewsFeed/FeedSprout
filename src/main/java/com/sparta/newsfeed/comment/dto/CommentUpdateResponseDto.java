package com.sparta.newsfeed.comment.dto;

import lombok.Getter;

@Getter
public class CommentUpdateResponseDto {
    private final String contents;

    public CommentUpdateResponseDto(String contents) {
        this.contents = contents;
    }
}
