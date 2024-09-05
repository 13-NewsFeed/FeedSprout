package com.sparta.newsfeed.user.entity;


import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.follow.entity.Follow;
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

    @Column(name = "username", nullable = false)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    // 게시글과의 일대다 양방향관계
    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();


    // 댓글과의 일대다 양방향관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();


    // 게시글 좋아요와의 일대다 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> postLikesjList = new ArrayList<>();


    // 댓글 좋아요와의 일대다 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> commentLikesList = new ArrayList<>();


    // 북마크와의 일대다 관계(북마크가 하나의 유저만 참조, 북마크 공유가 제한?)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarkList = new ArrayList<>();


    // 이미지와의 일대일 단방향 관계
    @OneToMany(mappedBy = "user")
    private List<Image> images;

    public User(UserRequestDto requestDto){

        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.username = requestDto.getUsername();

    }

    // 내가 팔로우한 애들
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Follow> followers = new ArrayList<>();

    // 니들이 나한테 건 팔로우
    @OneToMany(mappedBy = "followee", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Follow> followees = new ArrayList<>();

}
