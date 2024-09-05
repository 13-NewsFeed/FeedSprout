package com.sparta.newsfeed.post.service;


import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.config.exception.CustomException;
import com.sparta.newsfeed.config.exception.ErrorCode;
import com.sparta.newsfeed.post.dto.PostRequestDto;
import com.sparta.newsfeed.post.dto.PostResponseDto;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.follow.entity.Follow;
import com.sparta.newsfeed.user.entity.Image;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.follow.repository.FollowRepository;
import com.sparta.newsfeed.user.repository.ImageRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private ImageRepository imageRepository;


    @Transactional
    public PostResponseDto create(AuthUser authUser, PostRequestDto dto) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND));
        Post post = Post.createPost(dto, user);
        Post savedPost = postRepository.save(post);
        Image image = new Image(dto.getImage(), "post", user, post);
        imageRepository.save(image);

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
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("대상 게시글이 없습니다."));
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


    public List<PostResponseDto> getPostsByTime(AuthUser authUser, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("modifiedAt").descending());
        Page<Post> pages = postRepository.findByUserId(authUser.getId(), pageable);
        if (pages.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        return pages.stream().map(PostResponseDto::new).toList();
    }

    public List<PostResponseDto> getPostsByLikes(AuthUser authUser, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> pages = postRepository.findByUserIdOrderByLikesCountDesc(authUser.getId(), pageable);
        if (pages.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        return pages.stream().map(PostResponseDto::new).toList();
    }

    // 팔로우 전체 게시글 조회
    public List<PostResponseDto> getPostsByFollowedUsers(AuthUser authUser, int pageNo, int pageSize) {
        // 팔로우 목록 가져오기
        List<Long> followedUserIds = followRepository.findFolloweeIdsByFollowerId(authUser.getId());
        if (followedUserIds.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        // 페이지 요청 설정
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        // 팔로우한 사용자들의 게시물 가져오기
        Page<Post> postPage = postRepository.findByUserIdIn(followedUserIds, pageRequest);

        // 게시물을 DTO로 변환
        return postPage.stream()
                .map(PostResponseDto::new)
                .toList();
    }

    // 팔로우
    public List<PostResponseDto> getPostsByFollowedUser(AuthUser authUser, Long followeeId, int pageNo, int pageSize) {
        // 팔로우 관계 확인
        boolean isFollowing1 = followRepository.existsByFollowerIdAndFolloweeId(authUser.getId(), followeeId);
        boolean isFollowing2 = followRepository.existsByFollowerIdAndFolloweeId(followeeId, authUser.getId());

        if (!(isFollowing1 || isFollowing2)) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        // followeeId 유저의 게시글 조회
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> postPage = postRepository.findByUserId(followeeId, pageable);

        // 게시물을 DTO로 변환
        return postPage.stream()
                .map(PostResponseDto::new)
                .toList();
    }


    @Transactional
    public PostResponseDto update(Long postId, PostRequestDto dto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
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

    @Transactional
    public PostResponseDto delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
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

