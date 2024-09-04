package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.dto.FollowResponseDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.Follow;
import com.sparta.newsfeed.user.entity.FollowState;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.FollowRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFeatureService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    // 팔로우 걸기, followerId: 팔로우 거는 사람 / followeeId: 팔로우 당하는 사람
    public FollowResponseDto followUser(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new IllegalArgumentException());
        User followee = userRepository.findById(followeeId).orElseThrow(() -> new IllegalArgumentException());

        FollowState currentState = FollowState.WAITING;

        Follow follow = new Follow(currentState, follower, followee);
        Follow savedFollow = followRepository.save(follow);

        return new FollowResponseDto(savedFollow);
    }

    // 나한테 팔로우 건 애들 가져오기
    public List<String> getFollowers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());

        FollowState currentState = FollowState.FOLLOWING;

        List<String> followerList = user.getFollowers().stream()
                .filter(follow -> follow.getState() == currentState)
                .map(follow -> follow.getFollower().getNickname())
                .toList();

        return followerList;
    }

    // 내가 팔로우 건 애들 가져오기
    public List<String> getFollowees(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());

        FollowState currentState = FollowState.FOLLOWING;

        List<String> followeeList = user.getFollowees().stream()
                .filter(follow -> follow.getState() == currentState)
                .map(follow -> follow.getFollowee().getNickname())
                .toList();

        return followeeList;
    }


    // 팔로우 대기 목록 확인하기
    public List<String> getWaitingFollowers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());

        FollowState currentState = FollowState.WAITING;

        List<String> waitingList = user.getFollowers().stream()
                .filter(follow -> follow.getState() == currentState)
                .map(follow -> follow.getFollower().getNickname())
                .toList();

        return waitingList;
    }

    // 팔로우 승낙 혹은 거절하기
    public void updateFollowState(Long followerId, Long followeeId, String state) {
        Follow follow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId).orElseThrow(() -> new IllegalArgumentException());
        FollowState currentState = FollowState.valueOf(state);

        follow.setState(currentState);

    }

}
