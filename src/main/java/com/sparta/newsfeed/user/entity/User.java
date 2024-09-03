package com.sparta.newsfeed.user.entity;


import com.sparta.newsfeed.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;


    // 게시글과의 일대다 양방향관계
    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    // 댓글과의 일대다 양방향관계
    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    // 게시글 좋아요와의 일대다 관계
    @OneToMany(mappedBy = "user")
    private List<PostLikes> postLikesjList = new ArrayList<>();

    // 댓글 좋아요와의 일대다 관계
    @OneToMany(mappedBy = "user")
    private List<CommentLikes> commentLikesList = new ArrayList<>();

    // 팔로워와의 일대다 관계

    // 북마크와의 관계

    // 이미지와의 일대일 단방향 관계
    @OneToOne(mappedBy = "imageUrl")
    private Image image;

    public User(UserRequestDto requestDto){

        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();

    }


}
