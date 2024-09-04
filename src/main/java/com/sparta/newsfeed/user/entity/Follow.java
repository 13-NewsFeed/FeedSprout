package com.sparta.newsfeed.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "follow")
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FollowState state;

    // 팔로우를 나한테 건 애들
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    // 내가 팔로우를 건 애들
    @ManyToOne
    @JoinColumn(name = "followee_id")
    private User followee;

    public Follow(FollowState state, User follower, User followee) {
        this.state = state;
        this.follower = follower;
        this.followee = followee;
    }
}
