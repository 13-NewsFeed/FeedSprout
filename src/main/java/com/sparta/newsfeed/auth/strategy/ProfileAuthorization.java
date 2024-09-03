package com.sparta.newsfeed.auth.strategy;

import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;

import java.io.IOException;

public class ProfileAuthorization implements AuthorizationStrategy {
    private final UserRepository userRepository;

    public ProfileAuthorization(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isAuthorized(Claims info, Long userId) throws ServletException, IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        return user.getEmail().equals(info.getSubject());
    }
}
