package com.sparta.newsfeed.auth.filter;

import com.sparta.newsfeed.auth.strategy.AuthorizationStrategy;
import com.sparta.newsfeed.auth.strategy.CommentAuthorization;
import com.sparta.newsfeed.auth.strategy.PostAuthorization;
import com.sparta.newsfeed.auth.strategy.ProfileAuthorization;
import com.sparta.newsfeed.auth.util.JwtUtil;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class authFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request,
                            ServletResponse response,
                            FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String method = httpServletRequest.getMethod();
        String requestURI = httpServletRequest.getRequestURI();

        String a = httpServletRequest.getHeader("Authorization");
        log.info(a);

        if(StringUtils.hasText(requestURI) && (requestURI.startsWith("/auth/"))) {

            filterChain.doFilter(request, response);

        } else {

            String tokenValue = jwtUtil.getJwtFromHeader(httpServletRequest);
            if(StringUtils.hasText(tokenValue)) {

                // 토큰 검증
                if (!jwtUtil.validateToken(tokenValue)) {
                    throw new IllegalArgumentException("Token Error");
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

                customHandleRequest(requestURI, method, info);

                filterChain.doFilter(request, response);

            } else {
                throw new IllegalArgumentException("Not Found Token");
            }
        }
    }


    private void customHandleRequest(String requestURI, String method, Claims info) throws ServletException, IOException {
        if (method.equals("PUT") || method.equals("DELETE")) {
            Long id = extractResourceId(requestURI);
            boolean result = handlerRequest(requestURI).isAuthorized(info, id);
            if (!result) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
    }

    private AuthorizationStrategy handlerRequest(String requestURI) throws ServletException, IOException {
        AuthorizationStrategy authStrategy = null;

        String start = requestURI.split("/")[1];
        switch (start) {
            case "post": {
                authStrategy = new PostAuthorization(postRepository);
                break;
            }
            case "comment": {
                authStrategy = new CommentAuthorization(commentRepository);
                break;
            }
            case "profile": {
                authStrategy = new ProfileAuthorization(userRepository);
                break;
            }
            default: {
                throw new IllegalArgumentException("not found");
            }
        }
        return authStrategy;
    }

    private Long extractResourceId(String requestURI) {
        return Long.valueOf(requestURI.split("/")[2]);
    }
}
