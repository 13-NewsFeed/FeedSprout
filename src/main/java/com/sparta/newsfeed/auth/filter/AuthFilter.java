package com.sparta.newsfeed.auth.filter;

import com.sparta.newsfeed.auth.strategy.AuthorizationStrategy;
import com.sparta.newsfeed.auth.strategy.CommentAuthorization;
import com.sparta.newsfeed.auth.strategy.PostAuthorization;
import com.sparta.newsfeed.auth.strategy.ProfileAuthorization;
import com.sparta.newsfeed.auth.util.JwtUtil;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.config.exception.CustomException;
import com.sparta.newsfeed.config.exception.ErrorCode;
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
public class AuthFilter implements Filter {

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


        if(StringUtils.hasText(requestURI) && (requestURI.startsWith("/auth/"))) {

            filterChain.doFilter(request, response);

        } else {

            String tokenValue = jwtUtil.getJwtFromHeader(httpServletRequest);
            if(StringUtils.hasText(tokenValue)) {

                // 토큰 검증
                if (!jwtUtil.validateToken(tokenValue)) {
                    throw new CustomException(ErrorCode.UNAUTHORIZED);
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

                request.setAttribute("userId", info.get("userId"));

                customHandleRequest(requestURI, method, info);

                filterChain.doFilter(request, response);

            } else {
                throw new CustomException(ErrorCode.UNAUTHORIZED);
            }
        }
    }


    private void customHandleRequest(String requestURI, String method, Claims info) throws ServletException, IOException {
        if (method.equals("PUT") || method.equals("DELETE")) {
            Long id = extractResourceId(requestURI);
            boolean result = handlerRequest(requestURI).isAuthorized(info, id);
            if (!result) {
                throw new CustomException(ErrorCode.FORBIDDEN);
            }
        }
    }

    private AuthorizationStrategy handlerRequest(String requestURI) throws ServletException, IOException {
        AuthorizationStrategy authStrategy = null;

        if (requestURI.matches("^/posts/\\d+/comments/\\d+$")) {
            // "/posts/{postId}/comments/{commentId}" 패턴에 매칭
            authStrategy = new CommentAuthorization(commentRepository);

        } else if (requestURI.matches("^/posts/\\d+$")) {
            // "/posts/{postId}" 패턴에 매칭
            authStrategy = new PostAuthorization(postRepository);

        } else if (requestURI.matches("^/profiles/\\d*$")) {
            // "/profiles/{userId}" 패턴에 매칭
            authStrategy = new ProfileAuthorization(userRepository);

        } else {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        return authStrategy;
    }

    private Long extractResourceId(String requestURI) {
        String[] splits = requestURI.split("/");
        String resourceId = splits[splits.length-1];
        return Long.valueOf(resourceId);
    }
}
