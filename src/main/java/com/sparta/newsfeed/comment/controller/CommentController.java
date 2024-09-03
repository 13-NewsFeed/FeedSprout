package com.sparta.newsfeed.comment.controller;

import com.sparta.newsfeed.comment.dto.*;
import com.sparta.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
/*

    // 댓글 생성
    @PostMapping("/posts/{post-id}/comments")
    public ResponseEntity<CommentSaveResponseDto> addComment(
            @PathVariable(name = "post-id") Long postId, @RequestBody CommentSaveRequestDto commentSaveRequestDto
    ) {
        return ResponseEntity.ok(commentService.saveComment(postId, commentSaveRequestDto));
    }

    // 댓글 조회(전체)
    @GetMapping("/posts/{post-id}/comments")
    public ResponseEntity<List<CommentGetAllResponseDto>> getAllComments(@PathVariable(name = "post-id") Long postId) {
        return ResponseEntity.ok(commentService.getAllComments(postId));
    }

    // 댓글 수정
    @PutMapping("/posts/comments/{comment-id}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(
            @PathVariable(name = "comment-id") Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto
    ){
        return ResponseEntity.ok(commentService.updateComment(commentId, commentUpdateRequestDto));
    }

    // 댓글 삭제
    @DeleteMapping("/posts/comments/{comment-id}")
    public void deleteComment(@PathVariable(name = "comment-id") Long commentId){
        commentService.deleteComment(commentId);
    }

*/



}
