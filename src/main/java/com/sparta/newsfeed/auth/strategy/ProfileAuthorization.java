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

    // 해당 Claims에서 사용자 정보를 가져오고, 입력받은 Id를 이용하여 사용자를 가져와서 이 둘이 일치하는지 비교
    @Override
    public boolean isAuthorized(Claims info, Long userId) throws ServletException, IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        return user.getEmail().equals(info.getSubject());
    }
}
