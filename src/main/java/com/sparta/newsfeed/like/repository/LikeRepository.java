package com.sparta.newsfeed.like.repository;

import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.like.entity.Like;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserId(Long userid);

    Optional<Like> findByUserIdAndCommentId(Long userId, Long commentId);
}
