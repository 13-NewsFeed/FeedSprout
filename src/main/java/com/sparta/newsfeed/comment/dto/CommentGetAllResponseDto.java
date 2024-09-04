package com.sparta.newsfeed.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentGetAllResponseDto {
    private final Long id;
    private final String contents;
    private final Long postId;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private List<Long> reCommentIds;


    public CommentGetAllResponseDto(
            Long id,
            String contents,
            Long postId,
            Long userId,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            List<Long> reCommentIds
    ) {
        this.id = id;
        this.contents = contents;
        this.postId = postId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.reCommentIds = reCommentIds;
    }
}
