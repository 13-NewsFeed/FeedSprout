package com.sparta.newsfeed.follow.repository;

import com.sparta.newsfeed.follow.entity.Follow;
import com.sparta.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFolloweeIdAndState(Long followerId, Long followeeId, Enum state);
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    List<Long> findFolloweeIdsByFollowerId(Long followerId);

}


