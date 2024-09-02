package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.comment.dto.*;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

    // 댓글 생성 요청을 받아 저장
    @Transactional
    public CommentSaveResponseDto saveComment(Long postId, CommentSaveRequestDto commentSaveRequestDto) {
        Comment newComment = new Comment(
                commentSaveRequestDto.getContents(),
                postId,
                commentSaveRequestDto.getUserId()
        );
        Comment savedComment = commentRepository.save(newComment);

        return new CommentSaveResponseDto(
                savedComment.getId(),
                savedComment.getContents(),
                savedComment.getPostId(),
                savedComment.getUserId(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    // 특정 게시글의 댓글 전부 조회
    public List<CommentGetAllResponseDto> getAllComments(Long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);

        List<CommentGetAllResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentGetAllResponseDto dto = new CommentGetAllResponseDto(
                    comment.getId(),
                    comment.getContents(),
                    comment.getPostId(),
                    comment.getUserId(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    // 특정 댓글 내용 수정
    @Transactional
    public CommentUpdateResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("Comment not found")
        );
        comment.update(commentUpdateRequestDto.getContents());

        return new CommentUpdateResponseDto(
                comment.getId(),
                comment.getContents(),
                comment.getPostId(),
                comment.getUserId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
                );
    }

    // 특정 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }








}
