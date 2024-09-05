package com.sparta.newsfeed.like.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequestDto {
    public Long userId;
    public Long commentId;

    public LikeRequestDto(Long commentId, Long userId) {
        this.commentId = commentId;
        this.userId = userId;
    }

}
