package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.config.exception.CustomException;
import com.sparta.newsfeed.config.exception.ErrorCode;
import com.sparta.newsfeed.follow.ExistsFollow;
import com.sparta.newsfeed.follow.dto.FollowResponseDto;
import com.sparta.newsfeed.follow.entity.Follow;
import com.sparta.newsfeed.follow.entity.FollowState;
import com.sparta.newsfeed.follow.repository.FollowRepository;
import com.sparta.newsfeed.user.dto.BookmarkResponseDto;
import com.sparta.newsfeed.user.entity.Bookmark;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.BookmarkRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFeatureService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final BookmarkRepository bookmarkRepository;

    // 팔로우 걸기, followerId: 팔로우 거는 사람 / followeeId: 팔로우 당하는 사람
    public FollowResponseDto followUser(Long followerId, Long followeeId) {
        if (followerId == followeeId) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        User follower = userRepository.findById(followerId).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        User followee = userRepository.findById(followeeId).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        FollowState currentState = FollowState.WAITING;

        Follow follow = new Follow(currentState, follower, followee);

        ExistsFollow existsFollow = new ExistsFollow(followRepository);

        if (existsFollow.isFollowing(followerId, followeeId)) {
            throw new CustomException(ErrorCode.CONFLICT);
        }

        Follow savedFollow = followRepository.save(follow);
        return new FollowResponseDto(savedFollow);

    }


    // 팔로우 삭제
    public void deleteFollower(Long followerId, Long followeeId) {
        Boolean flag1 = followRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId);
        Boolean flag2 = followRepository.existsByFollowerIdAndFolloweeId(followeeId, followerId);

        Follow follow = null;

        if (flag1) {
            follow = followRepository.findByFollowerIdAndFolloweeIdAndState(followerId, followeeId, FollowState.FOLLOWING).get();
        } else if (flag2) {
            follow = followRepository.findByFollowerIdAndFolloweeIdAndState(followeeId, followerId, FollowState.FOLLOWING).get();
        } else {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        followRepository.delete(follow);
    }


    // 나랑 팔로우인 애들 가져오기
    public List<String> getFollowers(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        FollowState currentState = FollowState.FOLLOWING;

        List<String> followerList = user.getFollowers().stream()
                .filter(follow -> follow.getState() == currentState)
                .map(follow -> follow.getFollowee().getUsername())
                .toList();
        System.out.println(followerList);
        return followerList;
    }


    // 내가 팔로우 건 애들 가져오기
    public List<String> getFollowees(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        FollowState currentState = FollowState.FOLLOWING;

        List<String> followeeList = user.getFollowees().stream()
                .filter(follow -> follow.getState() == currentState)
                .map(follow -> follow.getFollower().getUsername())
                .toList();
        System.out.println(followeeList);
        return followeeList;
    }


    // 팔로우 대기 목록 확인하기
    public List<String> getWaitingFollowers(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        FollowState currentState = FollowState.WAITING;

        List<String> waitingList = user.getFollowees().stream()
                .filter(follow -> follow.getState() == currentState)
                .map(follow -> follow.getFollower().getUsername())
                .toList();

        return waitingList;
    }


    // 팔로우 승낙 혹은 거절하기
    public void updateFollowState(Long followerId, Long followeeId, String state) {

        Follow follow = followRepository.findByFollowerIdAndFolloweeIdAndState(followerId, followeeId, FollowState.WAITING)
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

    public List<BookmarkResponseDto> getBookmarks(Long userId) {

        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(userId);
        List<BookmarkResponseDto> bookmarkResponseDtoList = bookmarkList.stream()
                .map(bookmark -> new BookmarkResponseDto(bookmark, ""))
                .toList();

        return bookmarkResponseDtoList;
    }

    public BookmarkResponseDto getBookmark(Long userId, Long bookmarkId) {

        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).get();
        if(bookmark.getUser().getId() != userId) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        return new BookmarkResponseDto(bookmark, "북마크 찾기 성공적");
    }

    public String deleteBookmark(Long userId, Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).get();
        if(bookmark.getUser().getId() != userId) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        bookmarkRepository.delete(bookmark);

        return "북마크 삭제";
    }

}
