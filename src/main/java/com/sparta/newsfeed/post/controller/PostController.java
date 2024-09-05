package com.sparta.newsfeed.post.controller;


import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.config.exception.CustomException;
import com.sparta.newsfeed.config.exception.ErrorCode;
import com.sparta.newsfeed.post.dto.PostRequestDto;
import com.sparta.newsfeed.post.dto.PostResponseDto;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/posts")      // userId 게시글 작성
    public ResponseEntity<PostResponseDto> create(
            AuthUser authUser, @RequestBody PostRequestDto dto
    ) {
        try {
            PostResponseDto response = postService.create(authUser, dto);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/{postId}")       // 게시글 단건 조회
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        try {
            PostResponseDto response = postService.getpost(postId);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/editedDate")       // 게시글 조회 (수정일자 내림차순)
    public ResponseEntity<List<PostResponseDto>> getPostsByTime(
            AuthUser authUser,
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        try {
            List<PostResponseDto> response = postService.getPostsByTime(authUser, pageNo, pageSize);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/likes")       // 게시글 조회 (좋아요 많은순 내림차순)
    public ResponseEntity<List<PostResponseDto>> getPostsByLikes(
            AuthUser authUser,
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        try {
            List<PostResponseDto> response = postService.getPostsByLikes(authUser, pageNo, pageSize);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profiles/follows/posts")      // 팔로우 한 사람들 게시글 모두 조회
    public ResponseEntity<List<PostResponseDto>> getPostsByFollowUsers(
            AuthUser authUser,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        try {
            List<PostResponseDto> response = postService.getPostsByFollowedUsers(authUser, pageNo, pageSize);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("profiles/follows/{followeeId}/posts")      // 팔로우 한 사람 한명 게시글 모두 조회
    public ResponseEntity<List<PostResponseDto>> getPostsByFollowedUser(
            AuthUser authUser,
            @PathVariable Long followeeId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        try {
            List<PostResponseDto> response = postService.getPostsByFollowedUser(authUser, followeeId, pageNo, pageSize);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/posts/{postId}")      // 게시글 수정
    public ResponseEntity<PostResponseDto> update(@PathVariable Long postId, @RequestBody PostRequestDto dto) {
        try {
            PostResponseDto response = postService.update(postId, dto);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/posts/{postId}")       // 게시글 삭제
    public ResponseEntity<PostResponseDto> delete(@PathVariable Long postId) {
        try {
            PostResponseDto responseDto = postService.delete(postId);
            return ResponseEntity.ok(responseDto);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}






