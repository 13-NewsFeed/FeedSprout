package com.sparta.newsfeed.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReCommentGetResponseDto {
    private final Long id;
    private final String contents;
    private final Long commentId;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ReCommentGetResponseDto(Long id, String contents, Long commentId, Long userId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contents = contents;
        this.commentId = commentId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
