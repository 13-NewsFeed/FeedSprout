package com.sparta.newsfeed.follow.entity;

import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    // 팔로우를 거는 애들
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    // 팔로우를 받는 애들
    @ManyToOne
    @JoinColumn(name = "followee_id")
    private User followee;

    public Follow(FollowState state, User follower, User followee) {
        this.state = state;
        this.follower = follower;
        this.followee = followee;
    }
}
