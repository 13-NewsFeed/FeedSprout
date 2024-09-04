package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.comment.dto.*;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
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
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    // 댓글 생성 요청을 받아 저장
    @Transactional
    public CommentSaveResponseDto saveComment(Long postId, AuthUser authUser, CommentSaveRequestDto commentSaveRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("Post not found")
        );
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        Comment newComment = new Comment(
                commentSaveRequestDto.getContents(),
                post,
                user
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
    public List<CommentGetAllResponseDto> getAllComments(Long postId, AuthUser authUser) {
        List<Comment> commentList = commentRepository.findByPostId(postId);

        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        List<CommentGetAllResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentGetAllResponseDto dto = new CommentGetAllResponseDto(
                    comment.getId(),
                    comment.getContents(),
                    postId,
                    authUser.getId(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            dtoList.add(dto);
        }
        return dtoList;
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
                comment.getPost(),
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
