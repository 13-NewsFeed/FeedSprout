package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.follow.dto.FollowResponseDto;
import com.sparta.newsfeed.follow.entity.Follow;
import com.sparta.newsfeed.follow.entity.FollowState;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.follow.repository.FollowRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFeatureService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    // 팔로우 걸기, followerId: 팔로우 거는 사람 / followeeId: 팔로우 당하는 사람
    public FollowResponseDto followUser(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId).
                orElseThrow(() -> new IllegalArgumentException("followerId를 찾을 수 없습니다.."));
        User followee = userRepository.findById(followeeId).
                orElseThrow(() -> new IllegalArgumentException("followeeId를 찾을 수 없습니다."));

        FollowState currentState = FollowState.WAITING;

        Follow follow = new Follow(currentState, follower, followee);
        Follow savedFollow = followRepository.save(follow);

        return new FollowResponseDto(savedFollow);
    }


    // 팔로우 삭제
    public void deleteFollower(Long followerId, Long followeeId) {
        Follow follow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우와의 관계를 찾을 수 없습니다. followerId :" +
                        followerId + ", follweeId : " + followeeId));

        followRepository.delete(follow);
    }


    // 나랑 팔로우인 애들 가져오기
    public List<String> getFollowers(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("userId를 찾을 수 없습니다."));

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
                orElseThrow(() -> new IllegalArgumentException("userId를 찾을 수 없습니다."));

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
                orElseThrow(() -> new IllegalArgumentException("userId를 찾을 수 없습니다."));

        FollowState currentState = FollowState.WAITING;

        List<String> waitingList = user.getFollowers().stream()
                .filter(follow -> follow.getState() == currentState)
                .map(follow -> follow.getFollower().getUsername())
                .toList();

        return waitingList;
    }


    // 팔로우 승낙 혹은 거절하기
    public void updateFollowState(Long followerId, Long followeeId, String state) {

        Follow follow;
        try {
            follow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "팔로우 요청을 찾을 수 없습니다. followerId: " + followerId + ", followeeId: " + followeeId));
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException(
                    "팔로우 요청을 찾을 수 없습니다. followerId: " + followerId + ", followeeId: " + followeeId);
        }

        FollowState currentState;
        try {
            currentState = FollowState.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 팔로우 상태 값 : " + state, e);
            // 문자열이 유효한 값이 아니라면, 예외를 다시 던져 새로운 IllegalArgumentExceptio 값을 생성.
            // 잘못된 상태값을 알려주어 e로 원래 발생한 예외 포함
        }

        follow.setState(currentState);
        followRepository.save(follow);
    }
}
