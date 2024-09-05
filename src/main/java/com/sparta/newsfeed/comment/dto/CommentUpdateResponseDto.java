package com.sparta.newsfeed.comment.dto;

import com.sparta.newsfeed.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResponseDto {
    private final Long id;
    private final String contents;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentUpdateResponseDto(Long id, String contents, Long userId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contents = contents;
        this.userId = userId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
