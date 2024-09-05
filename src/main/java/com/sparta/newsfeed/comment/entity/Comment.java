package com.sparta.newsfeed.comment.entity;

import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replyComments;

    private Long depth;

    public Comment(String contents, Post post, User user, Comment parent, List<Comment> replys, Long depth) {
        this.contents = contents;
        this.post = post;
        this.user = user;
        this.parent = parent;
        this.replyComments = replys;
        this.depth = depth;
    }

    public void update(String contents){
        this.contents = contents;
    }

   
}
