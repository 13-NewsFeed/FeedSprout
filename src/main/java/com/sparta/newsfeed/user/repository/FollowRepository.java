package com.sparta.newsfeed.user.repository;

import com.sparta.newsfeed.user.entity.Follow;
import com.sparta.newsfeed.user.entity.FollowState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
