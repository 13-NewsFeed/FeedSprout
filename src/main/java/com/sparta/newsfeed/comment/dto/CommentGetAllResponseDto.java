package com.sparta.newsfeed.comment.dto;

import com.sparta.newsfeed.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentGetAllResponseDto {
    private final Long id;
    private final String contents;
    private final Long postId;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public CommentGetAllResponseDto(Long id, String contents, Long postId, Long userId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contents = contents;
        this.postId = postId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public CommentGetAllResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.postId = comment.getPost().getId();
        this.userId = comment.getUser().getId();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

}
