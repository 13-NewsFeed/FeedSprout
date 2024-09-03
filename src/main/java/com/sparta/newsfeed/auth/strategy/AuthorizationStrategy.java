package com.sparta.newsfeed.auth.strategy;

import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import java.io.IOException;

public interface AuthorizationStrategy {
    boolean isAuthorized(Claims info, Long resoureId) throws ServletException, IOException;
}
