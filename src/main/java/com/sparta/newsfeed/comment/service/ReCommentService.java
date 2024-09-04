package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.comment.dto.ReCommentSaveRequestDto;
import com.sparta.newsfeed.comment.dto.ReCommentSaveResponseDto;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.entity.ReComment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.comment.repository.ReCommentRepository;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReCommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReCommentRepository reCommentRepository;

    // 대댓글 생성 요청을 받아 저장
    @Transactional
    public ReCommentSaveResponseDto saveReComment(
            Long postId,
            Long commentId,
            AuthUser authUser,
            ReCommentSaveRequestDto reCommentSaveRequestDto
    ){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("Post not found")
        );

        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("Comment not found")
        );

        ReComment reComment = new ReComment(
                user,
                reCommentSaveRequestDto.getContents(),
                comment
        );
        ReComment savedReComment = reCommentRepository.save(reComment);

        return new ReCommentSaveResponseDto(
                savedReComment.getId(),
                savedReComment.getContents(),
                comment.getId(),
                authUser.getId(),
                savedReComment.getCreatedAt(),
                savedReComment.getModifiedAt()
        );
    }





}
