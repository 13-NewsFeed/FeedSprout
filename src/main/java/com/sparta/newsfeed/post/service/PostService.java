package com.sparta.newsfeed.post.service;


import com.sparta.newsfeed.post.dto.PostRequestDto;
import com.sparta.newsfeed.post.dto.PostResponseDto;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.entity.User;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.post.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public PostResponseDto create(Long userId, PostRequestDto dto) {
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

    public PostResponseDto getpost(Long postId) {
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


    public List<PostResponseDto> getPostsByTime(Long userId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by("modifiedAt").descending());
        return postRepository.findByUserId(userId, pageable)
                .stream()
                .map(PostResponseDto::new)
                .toList();
    }

    public List<PostResponseDto> getPostsByLikes(Long userId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        Page<Post> postPage = postRepository.findByUserIdOrderByLikesCountDesc(userId, pageable);
        // Page<Post> 결과를 PostResponseDto 리스트로 변환
        return postPage.stream()
                .map(PostResponseDto::new)
                .toList();
    }


    public PostResponseDto update(Long postId, PostRequestDto dto) {
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

    public PostResponseDto delete(Long postId) {
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
