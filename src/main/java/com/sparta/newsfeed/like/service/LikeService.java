package com.sparta.newsfeed.like.service;


import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.like.dto.LikeResponseDto;
import com.sparta.newsfeed.like.entity.Like;
import com.sparta.newsfeed.like.repository.LikeRepository;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    //게시글 좋아요,좋아요 취소
    public LikeResponseDto postLike(Long userid, Long postId)  {
        User user =userRepository.findById(userid)
                .orElseThrow(()->new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not found"));

        //유저가 해당 게시글에 대해 좋아요를 눌렀는지 확인
         Optional<Like> like= likeRepository.findByUserId(userid);

         if(like.isEmpty()){
             //좋아요가 없다.
             Like newLike = new Like(user,post,null);
             likeRepository.save(newLike);
             return new LikeResponseDto("좋아요",200);
         }else{
             //좋아요가 이미 존재하면 삭제
             likeRepository.delete(like.get());

             return new LikeResponseDto("좋아요 삭제",404);
         }
    }

    public LikeResponseDto commentLike(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new RuntimeException("Comment not found"));

        //댓글이 속해있는 게시글 조회
        Post post = comment.getPost();

        Optional<Like> like =likeRepository.findByUserIdAndCommentId(userId,commentId);

        if(like.isEmpty()){
            Like newLike = new Like(user,post,comment);  // <- 댓글에 게시글 조회한거 까지 넣음.
            likeRepository.save(newLike);
            return new LikeResponseDto("좋아요",200);
        }else {
            likeRepository.delete(like.get());
            return new LikeResponseDto("좋아요 취소",404);
        }
    }
    //1) 사용자가 게시글에 좋아요를 누를때 호출
    //2) 먼저 게시글 존재 확인 후, 사용자가 이미 좋아요 눌렀는지 확인
    //3) 적절한 응답 반환
    //4) 좋아요를 처음 누르는 경우 새로운 좋아요를 저장하고 게시글 좋아요 수를 증가.

    //post, comment 엔티티에 증가/감소 메서드 작성 해야됨.
    //increaseLikeCount , decreaseLikeCount

    //게시글 좋아요
}
