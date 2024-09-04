package com.sparta.newsfeed.like.entity;

import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter

@Entity
@NoArgsConstructor
@Table(name = "likey")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;


    public Like(User user, Post post, Comment comment){
       this.user = user;
       this.post = post;
       this.comment = comment;
    }
}
