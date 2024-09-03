package com.sparta.newsfeed.auth.strategy;

import com.sparta.newsfeed.comment.repository.CommentRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;

import java.io.IOException;

public class CommentAuthorization implements AuthorizationStrategy {
    private final CommentRepository commentRepository;
    
    public CommentAuthorization(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    
    
    @Override
    public boolean isAuthorized(Claims info, Long commentId) throws ServletException, IOException {
        User user = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException()).getUser();
        return user.getEmail().equals(info.getSubject());
    }
}
