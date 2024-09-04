package com.sparta.newsfeed.post.controller;


import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.post.dto.PostRequestDto;
import com.sparta.newsfeed.post.dto.PostResponseDto;
import com.sparta.newsfeed.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/posts")      // userId 게시글 작성
    public ResponseEntity<PostResponseDto> createPost(AuthUser authUser, @RequestBody PostRequestDto dto) {
        PostResponseDto response = postService.createPost(authUser, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/{postId}")       // 게시글 단건 조회
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/editedDate")       // 게시글 조회 (수정일자 내림차순)
    public ResponseEntity<List<PostResponseDto>> getPostsByTime(
            AuthUser authUser,
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        List<PostResponseDto> response = postService.getPostsByTime(authUser, pageNo, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/likes")       // 게시글 조회 (좋아요 많은순 내림차순)
    public ResponseEntity<List<PostResponseDto>> getPostsByLikes(
            AuthUser authUser,
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        List<PostResponseDto> response = postService.getPostsByLikes(authUser, pageNo, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profiles/{userId}/follows/{followerId}/posts")      // 게시글 조회 (팔로우)
    public ResponseEntity<List<PostResponseDto>> getFollowerPosts(
            @PathVariable(name = "followerId") Long followerId,
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        // 이부분 Service와 함께 작성 필요
        // List<PostResponseDto> response = postService.getFollowerPosts(followerId, pageNo, pageSize);
        return ResponseEntity.ok(null);
    }



    @PutMapping("/posts/{postId}")      // 게시글 수정
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto dto) {
        PostResponseDto response = postService.updatePost(postId, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/posts/{postId}")       // 게시글 삭제
    public ResponseEntity<PostResponseDto> deletePost(@PathVariable Long postId) {
        PostResponseDto responseDto = postService.deletePost(postId);
        return ResponseEntity.ok(responseDto);
    }


}






