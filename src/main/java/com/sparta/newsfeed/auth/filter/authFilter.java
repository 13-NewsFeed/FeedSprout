package com.sparta.newsfeed.auth.filter;

import com.sparta.newsfeed.auth.strategy.AuthorizationStrategy;
import com.sparta.newsfeed.auth.strategy.CommentAuthorization;
import com.sparta.newsfeed.auth.strategy.PostAuthorization;
import com.sparta.newsfeed.auth.strategy.ProfileAuthorization;
import com.sparta.newsfeed.auth.template.PostRequestHandler;
import com.sparta.newsfeed.auth.template.RequestHandler;
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


                /*// 게시글 수정 및 삭제
                if (requestURI.startsWith("/posts/")) {
                    if(requestURI.endsWith("likes")) {
                        if (method.equals("DELETE")) {

                            String postIdStr = requestURI.split("/")[2];
                            Long postId = Long.valueOf(postIdStr);
                            User user = postRepository.findById(postId).getUser();

                            if(isSameUser(info, user)) {
                                filterChain.doFilter(request, response);
                            } else {
                                throw new IllegalArgumentException();
                            }

                        } else {
                            filterChain.doFilter(request, response);
                        }
                    } else {
                        if(method.equals("UPDATE") || method.equals("DELETE")) {

                            String postIdStr = requestURI.split("/")[2];
                            Long postId = Long.valueOf(postIdStr);
                            User user = postRepository.findById(postId).getUser();

                            if(isSameUser(info, user)) {
                                filterChain.doFilter(request, response);
                            } else {
                                throw new IllegalArgumentException();
                            }

                        } else {
                            filterChain.doFilter(request, response);
                        }
                    }
                }
                // 댓글 수정 및 삭제
                else if (requestURI.startsWith("/comments/")) {
                    if(requestURI.endsWith("likes")) {
                        if (method.equals("DELETE")) {

                            String postIdStr = requestURI.split("/")[2];
                            Long postId = Long.valueOf(postIdStr);
                            User user = postRepository.findById(postId).getUser();

                            if(isSameUser(info, user)) {
                                filterChain.doFilter(request, response);
                            } else {
                                throw new IllegalArgumentException();
                            }

                        } else {
                            filterChain.doFilter(request, response);
                        }
                    } else {
                        if(method.equals("UPDATE") || method.equals("DELETE")) {

                            String commentIdStr = requestURI.split("/")[2];
                            Long commenttId = Long.valueOf(commentIdStr);
                            User user = commentRepository.findById(commenttId).getUser();

                            if(isSameUser(info, user)) {
                                filterChain.doFilter(request, response);
                            } else {
                                throw new IllegalArgumentException();
                            }
                        } else {
                            filterChain.doFilter(request, response);
                        }
                    }
                }

                else if (requestURI.startsWith("/profiles/")) {

                    // 팔로우 관리
                    if (requestURI.endsWith("follows") || requestURI.endsWith("state")) {
                        if(method.equals("UPDATE") || method.equals("DELETE")) {

                            String prodfileIdStr = requestURI.split("/")[2];
                            Long prodfileId = Long.valueOf(prodfileIdStr);
                            User user = postRepository.findById(prodfileId).getUser();

                            if(isSameUser(info, user)) {
                                filterChain.doFilter(request, response);
                            } else {
                                throw new IllegalArgumentException();
                            }

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

                            if(isSameUser(info, user)) {
                                filterChain.doFilter(request, response);
                            } else {
                                throw new IllegalArgumentException();
                            }

                        } else {
                            // profile 조회는 패스
                            filterChain.doFilter(request, response);
                        }
                    }
                }*/

                if (requestURI.startsWith("/posts/")) {
                    handlePostRequest(requestURI, method, info);
                } else if (requestURI.startsWith("/comments/")) {
                    handleCommentRequest(requestURI, method, info);
                } else if (requestURI.startsWith("/profiles/")) {
                    handleProfileRequest(requestURI, method, info);
                }

                filterChain.doFilter(request, response);

            } else {
                throw new IllegalArgumentException("Not Found Token");
            }
        }
    }

    /*private boolean isSameUser(Claims info, User user) throws ServletException, IOException {

        if(user.getEmail() == info.getSubject()) {
            return true;
        } else {
            return false;
        }
    }*/

    private void handlePostRequest(String requestURI, String method, Claims info) {
        if (method.equals("UPDATE") || method.equals("DELETE")) {
            Long postId = extractResourceId(requestURI);
            AuthorizationStrategy authStrategy = new PostAuthorization(postRepository);
            if (!authStrategy.isAuthorized(info, postId)) {
                throw new IllegalArgumentException("Unauthorized");
            }
        }
    }

    private void handleCommentRequest(String requestURI, String method, Claims info) {
        if (method.equals("UPDATE") || method.equals("DELETE")) {
            Long commentId = extractResourceId(requestURI);
            AuthorizationStrategy authStrategy = new CommentAuthorization(commentRepository);
            if (!authStrategy.isAuthorized(info, commentId)) {
                throw new IllegalArgumentException("Unauthorized");
            }
        }
    }

    private void handleProfileRequest(String requestURI, String method, Claims info) {
        if (method.equals("UPDATE") || method.equals("DELETE")) {
            Long profileId = extractResourceId(requestURI);
            AuthorizationStrategy authStrategy = new ProfileAuthorization(profileRepository);
            if (!authStrategy.isAuthorized(info, profileId)) {
                throw new IllegalArgumentException("Unauthorized");
            }
        }
    }

    private Long extractResourceId(String requestURI) {
        return Long.valueOf(requestURI.split("/")[2]);
    }
}
