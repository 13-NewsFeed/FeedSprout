package com.sparta.newsfeed.comment.comtroller;

import com.sparta.newsfeed.comment.dto.CommentGetAllResponseDto;
import com.sparta.newsfeed.comment.dto.CommentSaveRequestDto;
import com.sparta.newsfeed.comment.dto.CommentSaveResponseDto;
import com.sparta.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/posts/{post-id}/comments")
    public ResponseEntity<CommentSaveResponseDto> addComment(
            @PathVariable Long postId, @RequestBody CommentSaveRequestDto commentSaveRequestDto
    ) {
        return ResponseEntity.ok(commentService.saveComment(postId, commentSaveRequestDto));
    }

    // 댓글 조회(전체)
    @GetMapping("/posts/{post-id}/comments")
    public ResponseEntity<List<CommentGetAllResponseDto>> getAllComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getAllComments(postId));
    }




}
