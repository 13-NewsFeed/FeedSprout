package com.sparta.newsfeed.auth.strategy;

import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.follow.ExistsFollow;
import com.sparta.newsfeed.follow.entity.Follow;
import com.sparta.newsfeed.follow.repository.FollowRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;

import java.io.IOException;

public class FollowAuthorization implements AuthorizationStrategy {
    private final FollowRepository followRepository;

    public FollowAuthorization(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }
    @Override
    public boolean isAuthorized(Claims info, Long resoureId) throws ServletException, IOException {
        Long userId = Long.valueOf(info.get("userId", String.class));
        Boolean one = followRepository.existsByFollowerIdAndFolloweeId(userId,resoureId);
        Boolean two= followRepository.existsByFollowerIdAndFolloweeId(resoureId,userId);

        return (one || two);
    }
}
