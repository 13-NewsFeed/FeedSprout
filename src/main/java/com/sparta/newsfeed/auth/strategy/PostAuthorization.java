package com.sparta.newsfeed.auth.strategy;

import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;

import java.io.IOException;

public class PostAuthorization implements AuthorizationStrategy {
    private final PostRepository postRepository;

    public PostAuthorization(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public boolean isAuthorized(Claims info, Long postId) throws ServletException, IOException {
        User user = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException()).getUser();
        return user.getEmail().equals(info.getSubject());
    }
}
