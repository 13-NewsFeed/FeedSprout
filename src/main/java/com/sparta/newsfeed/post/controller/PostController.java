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
    public ResponseEntity<PostResponseDto> create(AuthUser authUser, @RequestBody PostRequestDto dto) {
        PostResponseDto response = postService.create(authUser, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/{postId}")       // 게시글 단건 조회
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto response = postService.getpost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/editedDate")       // 게시글 조회 (수정일자 내림차순)
    public ResponseEntity<List<PostResponseDto>> getPostsByTime(
            AuthUser authUser,
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize) {
        List<PostResponseDto> response = postService.getPostsByTime(authUser, pageNo, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/like")       // 게시글 조회 (좋아요 많은순 내림차순)
    public ResponseEntity<List<PostResponseDto>> getPostsByLikes(
            AuthUser authUser,
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize) {
        List<PostResponseDto> response = postService.getPostsByLikes(authUser, pageNo, pageSize);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/posts/follows/posts")      // 팔로우 한 사람들 게시글 모두 조회
    public ResponseEntity<List<PostResponseDto>> getPostsByFollowUsers(
            AuthUser authUser,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<PostResponseDto> response = postService.getPostsByFollowedUsers(authUser, pageNo, pageSize);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/posts/follows/{followeeId}/posts")      // 팔로우 한 사람 한명 게시글 모두 조회
    public ResponseEntity<List<PostResponseDto>> getPostsByFollowedUser(
            AuthUser authUser,
            @PathVariable Long followeeId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<PostResponseDto> response = postService.getPostsByFollowedUser(authUser, followeeId, pageNo, pageSize);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/posts/{postId}")      // 게시글 수정
    public ResponseEntity<PostResponseDto> update(@PathVariable Long postId, @RequestBody PostRequestDto dto) {
        PostResponseDto response = postService.update(postId, dto);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/posts/{postId}")       // 게시글 삭제
    public ResponseEntity<PostResponseDto> delete(@PathVariable Long postId) {
        PostResponseDto responseDto = postService.delete(postId);
        return ResponseEntity.ok(responseDto);
    }

}






