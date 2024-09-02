package com.sparta.newsfeed.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;
    private Long postId;
    private Long userId;

    public Comment(String contents, Long postId, Long userId) {
        this.contents = contents;
        this.postId = postId;
        this.userId = userId;
    }
}
