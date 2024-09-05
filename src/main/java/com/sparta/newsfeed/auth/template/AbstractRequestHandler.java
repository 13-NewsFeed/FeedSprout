package com.sparta.newsfeed.auth.template;

import com.sparta.newsfeed.auth.strategy.AuthorizationStrategy;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// resource와
public abstract class AbstractRequestHandler implements RequestHandler {
    protected final AuthorizationStrategy authorizationStrategy;

    protected AbstractRequestHandler(AuthorizationStrategy authorizationStrategy) {
        this.authorizationStrategy = authorizationStrategy;
    }

    @Override
    public void handleRequest(String method, String requestURI, Claims info, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Long resourceId = extractResourceId(requestURI);
        if(isAuthorized(info, resourceId)) {
            filterChain.doFilter(request, response);
        } else {
            throw new IllegalArgumentException("Unauthorized");
        }
    }

    protected abstract Long extractResourceId(String requestUri);

    private boolean isAuthorized(Claims info, Long resourceId) throws ServletException, IOException {
        return authorizationStrategy.isAuthorized(info, resourceId);
    }
}
