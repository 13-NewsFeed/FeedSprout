package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.comment.dto.*;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.config.exception.CustomException;
import com.sparta.newsfeed.config.exception.ErrorCode;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    // 댓글 생성 요청을 받아 저장
    @Transactional
    public CommentSaveResponseDto saveComment(Long postId, AuthUser authUser, CommentSaveRequestDto commentSaveRequestDto) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND)
        );

        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND)
        );


        Comment newComment = new Comment(
                commentSaveRequestDto.getContents(),
                post,
                user,
                null,
                null,
                0L
        );
        Comment savedComment = commentRepository.save(newComment);

        return new CommentSaveResponseDto(
                savedComment.getId(),
                savedComment.getContents(),
                postId,
                authUser.getId(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    // 대댓글 생성 요청을 받아 저장
    @Transactional
    public CommentSaveResponseDto saveReplyComment(Long postId, Long commentId, AuthUser authUser, CommentSaveRequestDto commentSaveRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        Comment parent = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        if (3 < parent.getDepth()) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        Comment newComment = new Comment(
                commentSaveRequestDto.getContents(),
                post,
                user,
                parent,
                null,
                parent.getDepth()+1L
        );

        Comment savedComment = commentRepository.save(newComment);

        return new CommentSaveResponseDto(
                savedComment.getId(),
                savedComment.getContents(),
                postId,
                authUser.getId(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    // 특정 게시글의 댓글 전부 조회
    public List<CommentGetAllResponseDto> getAllComments(
            Long postId,
            AuthUser authUser,
            int page,
            int size
    ) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("Post not found")
        );

        Pageable pageable = PageRequest.of(page - 1, size);


        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);

        return comments.getContent().stream().map(
                comment -> new CommentGetAllResponseDto(
                comment.getId(),
                comment.getContents(),
                comment.getPost().getId(),
                comment.getUser().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        )).toList();

    }

    // 특정 댓글 내용 수정
    @Transactional
    public CommentUpdateResponseDto updateComment(Long commentId, AuthUser authUser,CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("Comment not found")
        );
        comment.update(commentUpdateRequestDto.getContents());
        return new CommentUpdateResponseDto(
                commentId,
                comment.getContents(),
                authUser.getId(),
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
