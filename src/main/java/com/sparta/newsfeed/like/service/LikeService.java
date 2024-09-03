package com.sparta.newsfeed.like.service;

import com.sparta.newsfeed.like.dto.StatusResult;
import com.sparta.newsfeed.like.entity.Like;
import com.sparta.newsfeed.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final JwtUtil jwtUtil;


    //1) 사용자가 게시글에 좋아요를 누를때 호출
    //2) 먼저 게시글 존재 확인 후, 사용자가 이미 좋아요 눌렀는지 확인
    //3) 적절한 응답 반환
    //4) 좋아요를 처음 누르는 경우 새로운 좋아요를 저장하고 게시글 좋아요 수를 증가.

    //post, comment 엔티티에 증가/감소 메서드 작성 해야됨.
    //increaseLikeCount , decreaseLikeCount

    //게시글 좋아요
    public StatusResult likePost(Long id, User user){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다..")
        );

        Optional<Like> checkUserAndPost = likeRepository.findByUserAndPost(user, post);

        if (checkUserAndPost.isPresent()) {
            return StatusResult.builder()
                    .msg("이미 좋아요를 누르셨습니다.")
                    .code(400)
                    .build();
        } else {
            likeRepository.save(new Like(user, post));
            post.increaseLikeCount(); //
            return StatusResult.builder()
                    .msg("해당 게시글을 좋아합니다.")
                    .code(200)
                    .build();
        }
    }

    // 게시글 좋아요 취소
    @Transactional
    public StatusResult deleteLikePost(Long id, User user) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Optional<Like> checkUserAndPost = likeRepository.findByUserAndPost(user, post);

        if (!checkUserAndPost.isPresent()) {
            return StatusResult.builder()
                    .msg("좋아요가 존재하지 않습니다.")
                    .code(400)
                    .build();
        } else {
            likeRepository.delete(checkUserAndPost.get());
            post.decreaseLikeCount(); //
            return StatusResult.builder()
                    .msg("좋아요 취소되었습니다.")
                    .code(200)
                    .build();
        }
    }

    // 댓글 좋아요
    @Transactional
    public StatusResult likeComment(Long id, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        Optional<Like> checkUserAndComment = likeRepository.findByUserAndComment(user, comment);

        if (checkUserAndComment.isPresent()) {
            return StatusResult.builder()
                    .msg("이미 좋아요를 누르셨습니다.")
                    .code(400)
                    .build();
        } else {
            likeRepository.save(new Like(user, comment));
            comment.increaseLikeCount(); //
            return StatusResult.builder()
                    .msg("해당 댓글을 좋아합니다.")
                    .code(200)
                    .build();
        }
    }

    // 댓글 좋아요 취소
    @Transactional
    public StatusResult deleteLikeComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        Optional<Like> checkUserAndComment = likeRepository.findByUserAndComment(user, comment);

        if (!checkUserAndComment.isPresent()) {
            return StatusResult.builder()
                    .msg("좋아요가 존재하지 않습니다.")
                    .code(400)
                    .build();
        } else {
            likeRepository.delete(checkUserAndComment.get());
            comment.decreaseLikeCount(); //
            return StatusResult.builder()
                    .msg("좋아요가 취소되었습니다.")
                    .code(200)
                    .build();
        }
    }
}
