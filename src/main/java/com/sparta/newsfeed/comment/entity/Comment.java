package com.sparta.newsfeed.comment.entity;

import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;

    public Comment(String contents, Long postId, Long userId) {
        this.contents = contents;
    }

    public void update(String contents){
        this.contents = contents;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
