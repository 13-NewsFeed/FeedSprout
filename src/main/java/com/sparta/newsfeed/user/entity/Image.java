package com.sparta.newsfeed.user.entity;

import com.sparta.newsfeed.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "image")
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "imageUrl")
    private String imageUrl;
    @Column
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Image(String imageUrl, User user) {
        this.imageUrl = imageUrl;
        this.user = user;
    }
}
