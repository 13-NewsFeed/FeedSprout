package com.sparta.newsfeed.post.entity;


import com.sparta.newsfeed.like.entity.Like;
import com.sparta.newsfeed.post.dto.PostRequestDto;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    // 0개
    // 재현 조아요 -> 1개
    // 태준 조아요 -> 2개
    // 태준 해제 -> 1개



    public static Post createPost(PostRequestDto dto, User user) {
        return new Post(
                user,
                dto.getTitle(),
                dto.getContents()
        );
    }

    public Post(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public void update(String title, String contents) {
        if (title != null) {
            this.title = title;
        }
        if (contents != null) {
            this.contents = contents;
        }
    }

}
