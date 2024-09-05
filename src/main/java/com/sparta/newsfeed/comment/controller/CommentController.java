package com.sparta.newsfeed.comment.controller;

import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.comment.dto.*;
import com.sparta.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<CommentSaveResponseDto> addComment(
            @PathVariable(name = "postId") Long postId, AuthUser authUser, @RequestBody CommentSaveRequestDto commentSaveRequestDto
    ) {
        return ResponseEntity.ok(commentService.saveComment(postId, authUser, commentSaveRequestDto));
    }

    // 대댓글 생성
    @PostMapping("/comments/{commentId}")
    public ResponseEntity<CommentSaveResponseDto> addReplyComment(
            @PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId, AuthUser authUser, @RequestBody CommentSaveRequestDto commentSaveRequestDto
    ) {
        return ResponseEntity.ok(commentService.saveReplyComment(postId, commentId, authUser, commentSaveRequestDto));
    }

    // 댓글 조회(전체)
    @GetMapping("/comments")
    public ResponseEntity<List<CommentGetAllResponseDto>> getAllComments(
            @PathVariable(name = "postId") Long postId,
            AuthUser authUser,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(commentService.getAllComments(postId, authUser, page, size));
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(
            @PathVariable(name = "commentId") Long commentId, AuthUser authUser,@RequestBody CommentUpdateRequestDto commentUpdateRequestDto
    ){
        return ResponseEntity.ok(commentService.updateComment(commentId, authUser, commentUpdateRequestDto));
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable(name = "commentId") Long commentId){
        commentService.deleteComment(commentId);
    }




}
