package com.sparta.newsfeed.user.dto;

import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.user.entity.Bookmark;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkResponseDto {

    private Long id;
    private Long userId;
    private Long postId;
    private String message;

    public BookmarkResponseDto(Bookmark bookmark, String msg) {
        this.id = bookmark.getId();
        this.userId = bookmark.getUser().getId();
        this.postId = bookmark.getPost().getId();
        this.message = msg;
    }
}
