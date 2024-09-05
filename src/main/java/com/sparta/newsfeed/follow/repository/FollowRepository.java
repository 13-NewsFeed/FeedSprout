package com.sparta.newsfeed.follow.repository;

import com.sparta.newsfeed.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    List<Long> findFolloweeIdsByFollowerId(Long followerId);

  /*  // 나와 팔로우인 애들의 게시물 가져오기
    @Query("SELECT post FROM Post post WHERE post.user IN (" +
            "SELECT follow.followee FROM Follow follow WHERE follow.follower = :user " +
            "UNION " +
            "SELECT follow.follower FROM Follow follow WHERE follow.followee = :user)")
    List<Post> findPostsByFollowingOrFollowers(@Param("user") User user);*/
}


