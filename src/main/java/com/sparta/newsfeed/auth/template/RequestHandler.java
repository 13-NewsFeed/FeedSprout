package com.sparta.newsfeed.auth.template;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RequestHandler {
    void handleRequest(String method, String requestURI, Claims info, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException;
}
