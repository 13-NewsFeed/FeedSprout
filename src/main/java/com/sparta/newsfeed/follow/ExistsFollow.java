package com.sparta.newsfeed.follow;

import com.sparta.newsfeed.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExistsFollow {

    private final FollowRepository followRepository;

    public boolean isFollowing(Long followerId, Long followeeId) {
        if (followRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId) ||
                followRepository.existsByFollowerIdAndFolloweeId(followeeId, followerId)) {
            return true;
        }
        return false;
    }
}
