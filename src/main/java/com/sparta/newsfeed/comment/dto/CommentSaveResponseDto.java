package com.sparta.newsfeed.comment.dto;

import lombok.Getter;

@Getter
public class CommentSaveResponseDto {
    private final Long id;
    private final String contents;
    private final Long postId;
    private final Long userId;

    public CommentSaveResponseDto(Long id, String contents, Long postId, Long userId) {
        this.id = id;
        this.contents = contents;
        this.postId = postId;
        this.userId = userId;
    }
}
