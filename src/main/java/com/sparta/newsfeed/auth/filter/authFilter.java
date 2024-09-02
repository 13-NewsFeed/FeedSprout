package com.sparta.newsfeed.auth.filter;

import com.sparta.newsfeed.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@Order(2)
@RequiredArgsConstructor
public class authFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request,
                            ServletResponse response,
                            FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String method = httpServletRequest.getMethod();
        String requestURI = httpServletRequest.getRequestURI();

        String a = httpServletRequest.getHeader("Authorization");
        log.info(a);

        if(StringUtils.hasText(requestURI) &&
                (requestURI.startsWith("/auth/login") || requestURI.startsWith("/auth/register"))) {

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



                // 게시글 수정 및 삭제
                if (requestURI.startsWith("/posts/") && !requestURI.endsWith("likes")) {

                    if(method.equals("UPDATE") || method.equals("DELETE")) {

                        String postIdStr = requestURI.split("/")[2];
                        Long postId = Long.valueOf(postIdStr);
                        User user = postRepository.findById(postId).getUser();

                        isSameUser(info, user, request, response, filterChain);
                    } else {
                        filterChain.doFilter(request, response);
                    }

                }
                // 댓글 수정 및 삭제
                else if (requestURI.startsWith("/comments/}") && !requestURI.endsWith("likes")) {

                    if(method.equals("UPDATE") || method.equals("DELETE")) {

                        String commentIdStr = requestURI.split("/")[2];
                        Long commenttId = Long.valueOf(commentIdStr);
                        User user = commentRepository.findById(commenttId).getUser();

                        isSameUser(info, user, request, response, filterChain);
                    } else {
                        filterChain.doFilter(request, response);
                    }
                }

                else if (requestURI.startsWith("/profiles/")) {

                    // 팔로우 관리
                    if (requestURI.endsWith("follows") || requestURI.endsWith("state")) {
                        if(method.equals("UPDATE") || method.equals("DELETE")) {

                            String prodfileIdStr = requestURI.split("/")[2];
                            Long prodfileId = Long.valueOf(prodfileIdStr);
                            User user = postRepository.findById(prodfileId).getUser();

                            isSameUser(info, user, request, response, filterChain);

                        } else {
                            // follow 조회는 패스
                            filterChain.doFilter(request, response);
                        }

                    } else {

                        // 프로필 수정
                        if(method.equals("UPDATE")) {

                            String prodfileIdStr = requestURI.split("/")[2];
                            Long prodfileId = Long.valueOf(prodfileIdStr);
                            User user = postRepository.findById(prodfileId).getUser();

                            isSameUser(info, user, request, response, filterChain);

                        } else {
                            // profile 조회는 패스
                            filterChain.doFilter(request, response);
                        }
                    }


                }



            } else {
                throw new IllegalArgumentException("Not Found Token");
            }
        }
    }

    public void isSameUser(Claims info, User user,
                           ServletRequest request,
                           ServletResponse response,
                           FilterChain filterChain) throws ServletException, IOException {

        if(user.getEmail() == info.getSubject()) {
            filterChain.doFilter(request, response);;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
