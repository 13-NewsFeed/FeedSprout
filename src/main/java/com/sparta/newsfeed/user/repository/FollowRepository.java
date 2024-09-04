package com.sparta.newsfeed.user.repository;

import com.sparta.newsfeed.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
