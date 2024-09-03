package com.sparta.newsfeed.post.service;


import com.sparta.newsfeed.post.dto.PostRequestDto;
import com.sparta.newsfeed.post.dto.PostResponseDto;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public PostResponseDto createPost(Long userId, PostRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("대상 유저가 없습니다."));
        Post post = Post.createPost(dto, user);
        Post savedPost = postRepository.save(post);


        return new PostResponseDto(
                savedPost.getId(),
                savedPost.getUser().getId(),
                savedPost.getTitle(),
                savedPost.getContents(),
                savedPost.getCreatedAt(),
                savedPost.getModifiedAt()
        );

    }

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("대상 게시글이 없습니다."));
        PostResponseDto responseDto = new PostResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
        return responseDto;
    }

    public PostResponseDto updatePost(Long postId, PostRequestDto dto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("대상 게시글이 없습니다."));
        post.update(
                dto.getTitle(),
                dto.getContents()
        );
        postRepository.save(post);
        return new PostResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );

    }

    public PostResponseDto deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("대상 게시글이 없습니다."));
        postRepository.delete(post);
        return new PostResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );

    }
}
