package com.sparta.newsfeed.post.controller;


import com.sparta.newsfeed.auth.dto.AuthUser;
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

    @GetMapping("/profiles/{userId}/follows/posts")
    public ResponseEntity<List<PostResponseDto>> getPostsByFollowUsers(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<PostResponseDto> posts = postService.getPostsByFollowedUsers(userId, page, size);
        return ResponseEntity.ok(posts);

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






