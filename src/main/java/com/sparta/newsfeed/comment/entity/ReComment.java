package com.sparta.newsfeed.comment.entity;

import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReComment extends Timestamped{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ReComment(User user, String contents, Comment comment) {
        this.user = user;
        this.contents = contents;
        this.comment = comment;
    }

    public void update(String contents){
        this.contents = contents;
    }
}
