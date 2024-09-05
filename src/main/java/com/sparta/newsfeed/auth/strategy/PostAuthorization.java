package com.sparta.newsfeed.auth.strategy;

import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;

import java.io.IOException;

public class PostAuthorization implements AuthorizationStrategy {
    private final PostRepository postRepository;

    public PostAuthorization(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 해당 Claims에서 사용자 정보를 가져오고, 입력받은 Id를 이용하여 사용자를 가져와서 이 둘이 일치하는지 비교
    @Override
    public boolean isAuthorized(Claims info, Long postId) throws ServletException, IOException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException());
        User user = post.getUser();
        return user.getEmail().equals(info.getSubject());
    }
}

// 필터에 요구사항이 많이 들어가서는 안된다.
