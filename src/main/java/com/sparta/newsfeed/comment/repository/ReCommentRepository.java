package com.sparta.newsfeed.comment.repository;

import com.sparta.newsfeed.comment.entity.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {
}
