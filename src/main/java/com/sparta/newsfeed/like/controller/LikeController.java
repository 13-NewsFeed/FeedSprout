package com.sparta.newsfeed.like.controller;

import com.sparta.newsfeed.like.dto.LikeResponseDto;
import com.sparta.newsfeed.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.sparta.newsfeed.like.dto.StatusResult; 
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/likes/{userId}")
public class LikeController {
    private final LikeService likeService;
    //게시글 좋아요
    @PostMapping("/posts/{postId}")
    public ResponseEntity<LikeResponseDto> postLike(
            @PathVariable Long userId , @PathVariable Long postId){

        LikeResponseDto response= likeService.postLike(userId,postId);

        return ResponseEntity.ok(response);
    }
    //HTTP 상태 코드 제어:
    // ResponseEntity를 사용하면 응답에 대한 HTTP 상태 코드를 명시적으로 지정할 수 있습니다.
    //상태 코드를 넣어야 되나?
    @PostMapping("/posts/{postId}/comments/{commentId}")
    public  ResponseEntity<LikeResponseDto> commentLike
            (@PathVariable Long userId, @PathVariable Long postId, @PathVariable Long commentId){

        LikeResponseDto response = likeService.commentLike(userId,commentId);

        return ResponseEntity.ok(response);
    }
}
