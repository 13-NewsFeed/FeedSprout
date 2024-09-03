package com.sparta.newsfeed.auth.strategy;

import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import java.io.IOException;

// 게시글, 댓글, 프로필 등에 대한 검사 로직에 대한 인터페이스
public interface AuthorizationStrategy {
    boolean isAuthorized(Claims info, Long resoureId) throws ServletException, IOException;
}

// ENUM으로 설정
// OWNER or ADMIN