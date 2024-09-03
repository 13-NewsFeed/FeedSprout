package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.dto.FollowResponseDto;
import com.sparta.newsfeed.user.entity.Follow;
import com.sparta.newsfeed.user.entity.FollowState;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.FollowRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFeatureService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public FollowResponseDto followUser(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new IllegalArgumentException());
        User followee = userRepository.findById(followeeId).orElseThrow(() -> new IllegalArgumentException());

        FollowState currentState = FollowState.FOLLOWING;

        Follow follow = new Follow(currentState, follower, followee);
        Follow savedFollow = followRepository.save(follow);

        return new FollowResponseDto(savedFollow);
    }

    /*public Follow findFollows(Long userId) {
        User followee = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());

    }*/
}
