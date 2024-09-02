package com.sparta.newsfeed.comment.comtroller;

import com.sparta.newsfeed.comment.dto.CommentSaveRequestDto;
import com.sparta.newsfeed.comment.dto.CommentSaveResponseDto;
import com.sparta.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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





}
