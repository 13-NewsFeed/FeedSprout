package com.sparta.newsfeed.comment.entity;

import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<ReComment> reComments = new ArrayList<>();

    public Comment(String contents, Post post, User user) {
        this.contents = contents;
        this.post = post;
        this.user = user;
    }


    public void update(String contents){
        this.contents = contents;
    }

   
}
