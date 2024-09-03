package com.sparta.newsfeed.auth.strategy;

import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;

import java.io.IOException;

public class CommentAuthorization implements AuthorizationStrategy {
    private final CommentRepository commentRepository;
    
    public CommentAuthorization(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    
    // 해당 Claims에서 사용자 정보를 가져오고, 입력받은 Id를 이용하여 사용자를 가져와서 이 둘이 일치하는지 비교
    @Override
    public boolean isAuthorized(Claims info, Long commentId) throws ServletException, IOException {
        User user = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException()).getUser();
        return user.getEmail().equals(info.getSubject());
    }
}
