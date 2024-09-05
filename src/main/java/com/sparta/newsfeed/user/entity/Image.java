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

    @OneToOne(mappedBy = "profileImage")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Image(String imageUrl, String category, User user, Post post) {
        this.imageUrl = imageUrl;
        this.category = category;
        this.user = user;
        this.post = post;
    }
}