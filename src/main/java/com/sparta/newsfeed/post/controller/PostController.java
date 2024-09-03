package com.sparta.newsfeed.post.controller;


import com.sparta.newsfeed.post.dto.PostRequestDto;
import com.sparta.newsfeed.post.dto.PostResponseDto;
import com.sparta.newsfeed.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/posts/{userId}")      // userId 게시글 작성
    public ResponseEntity<PostResponseDto> createPost(@PathVariable Long userId, @RequestBody PostRequestDto dto) {
        PostResponseDto response = postService.createPost(userId, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/{postId}")       // 게시글 단건 조회
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/posts/edited-date")      // 게시글 조회 (수정일자)
//
//    @GetMapping("/posts/likes")      // 게시글 조회 (좋아요) 많은순
//
//    @GetMapping("/profiles/{id}/follows/posts")      // 게시글 조회 (팔로우)
//
//    @PostMapping("/posts/{id}/likes")      // 게시글 좋아요 등록
//
//    @DeleteMapping("/posts/{id}/likes")       // 게시글 좋아요 삭제


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
