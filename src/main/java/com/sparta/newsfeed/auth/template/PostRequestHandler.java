package com.sparta.newsfeed.auth.template;

import com.sparta.newsfeed.auth.strategy.AuthorizationStrategy;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PostRequestHandler extends AbstractRequestHandler {

    public PostRequestHandler(AuthorizationStrategy authorizationStrategy) {
        super(authorizationStrategy);
    }

    @Override
    protected Long extractResourceId(String requestUri) {
        return Long.valueOf(requestUri.split("/")[2]);
    }
}
