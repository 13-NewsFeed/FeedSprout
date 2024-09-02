package com.sparta.newsfeed.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentSaveResponseDto {
    private final Long id;
    private final String contents;
    private final Long postId;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentSaveResponseDto(Long id, String contents, Long postId, Long userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.contents = contents;
        this.postId = postId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
