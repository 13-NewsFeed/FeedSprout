package com.sparta.newsfeed.comment.controller;

import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.comment.dto.ReCommentGetResponseDto;
import com.sparta.newsfeed.comment.dto.ReCommentSaveRequestDto;
import com.sparta.newsfeed.comment.dto.ReCommentSaveResponseDto;
import com.sparta.newsfeed.comment.entity.ReComment;
import com.sparta.newsfeed.comment.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/comments/{commentId}")
@RequiredArgsConstructor
public class ReCommentController {
    private final ReCommentService reCommentService;

    // 대댓글 생성
    @PostMapping("/recomments")
    public ResponseEntity<ReCommentSaveResponseDto> addReComment(
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId,
            AuthUser authUser,
            @RequestBody ReCommentSaveRequestDto reCommentSaveRequestDto
            ){
        return ResponseEntity.ok(reCommentService.saveReComment(
                postId, commentId, authUser, reCommentSaveRequestDto
        ));
    }

    // 대댓글 개별 조회
    @GetMapping("/recomments/{recommentId}")
    public ResponseEntity<ReCommentGetResponseDto> getReComment(
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId,
            AuthUser authUser,
            @PathVariable(name = "recommentId") Long recommentId
    ){
        return ResponseEntity.ok(reCommentService.getReComment(
                postId, commentId, authUser, recommentId
        ));
    }

    // 대댓글 수정

    // 대댓글 삭제





}
