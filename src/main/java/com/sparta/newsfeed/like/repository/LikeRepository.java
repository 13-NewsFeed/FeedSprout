package com.sparta.newsfeed.like.repository;

import com.sparta.newsfeed.like.entity.Like;
import com.sparta.newsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Post, Long>  {
}
