package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.config.exception.CustomException;
import com.sparta.newsfeed.config.exception.ErrorCode;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.user.dto.FollowResponseDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.Follow;
import com.sparta.newsfeed.user.entity.FollowState;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.FollowRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
        User follower = userRepository.findById(followerId).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        User followee = userRepository.findById(followeeId).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        FollowState currentState = FollowState.WAITING;

        Follow follow = new Follow(currentState, follower, followee);
        Follow savedFollow = followRepository.save(follow);

        return new FollowResponseDto(savedFollow);
    }


    // 팔로우 삭제
    public void deleteFollower(Long followerId, Long followeeId) {
        Follow follow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        followRepository.delete(follow);
    }


    // 나한테 팔로우 건 애들 가져오기
    public List<String> getFollowers(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        FollowState currentState = FollowState.FOLLOWING;

        List<String> followerList = user.getFollowers().stream()
                .filter(follow -> follow.getState() == currentState)
                .map(follow -> follow.getFollower().getUsername())
                .toList();

        return followerList;
    }


    // 내가 팔로우 건 애들 가져오기
    public List<String> getFollowees(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        FollowState currentState = FollowState.FOLLOWING;

        List<String> followeeList = user.getFollowees().stream()
                .filter(follow -> follow.getState() == currentState)
                .map(follow -> follow.getFollowee().getUsername())
                .toList();

        return followeeList;
    }


    // 팔로우 대기 목록 확인하기
    public List<String> getWaitingFollowers(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        FollowState currentState = FollowState.WAITING;

        List<String> waitingList = user.getFollowers().stream()
                .filter(follow -> follow.getState() == currentState)
                .map(follow -> follow.getFollower().getUsername())
                .toList();

        return waitingList;
    }


    // 팔로우 승낙 혹은 거절하기
    public void updateFollowState(Long followerId, Long followeeId, String state) {

        Follow follow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        FollowState currentState;
        try {
            currentState = FollowState.valueOf(state.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        follow.setState(currentState);
        followRepository.save(follow);

        }
    }
