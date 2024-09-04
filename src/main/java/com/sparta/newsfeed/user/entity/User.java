package com.sparta.newsfeed.user.entity;


import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.like.entity.Like;
import com.sparta.newsfeed.post.entity.Post;
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
@Table(name = "users")
@NoArgsConstructor
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "newpassword", nullable = false)
    private String newPassword;

    @Column(name = "username", nullable = false)
    private String username;

    // 게시글과의 일대다 양방향관계
    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();


    // 댓글과의 일대다 양방향관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();


/*    // 북마크와의 일대다 관계(북마크가 하나의 유저만 참조, 북마크 공유가 제한?)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    // 이미지와의 일대일 양방향 관계
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Image image;*/

    // 좋아요와의 일대다 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> Like = new ArrayList<>();


    // 내가 팔로우한 애들
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Follow> followers = new ArrayList<>();
    // 니들이 나한테 건 팔로우
    @OneToMany(mappedBy = "followee", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Follow> followees = new ArrayList<>();


    public User(UserRequestDto requestDto, String encodedPassword){
        this.password = encodedPassword;
        this.email = requestDto.getEmail();
        this.username = requestDto.getUsername();

    }
}
