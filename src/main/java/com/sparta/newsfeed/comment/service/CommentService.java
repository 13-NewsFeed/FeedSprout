package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.comment.dto.CommentSaveRequestDto;
import com.sparta.newsfeed.comment.dto.CommentSaveResponseDto;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    // 댓글 생성 요청을 받아 저장
    @Transactional
    public CommentSaveResponseDto saveComment(Long postId, CommentSaveRequestDto commentSaveRequestDto) {
        Comment newComment = new Comment(commentSaveRequestDto.getContents(), postId, commentSaveRequestDto.getUserId());
        Comment savedComment = commentRepository.save(newComment);

        return new CommentSaveResponseDto(savedComment.getId(), savedComment.getContents(),savedComment.getPostId(),savedComment.getUserId());

    }






}
