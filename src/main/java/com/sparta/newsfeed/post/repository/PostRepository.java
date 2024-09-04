package com.sparta.newsfeed.post.repository;


import com.sparta.newsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserId(Long userId, Pageable pageable);
    @Query("SELECT p FROM Post p LEFT JOIN p.likes l WHERE p.user.id = :userId GROUP BY p.id ORDER BY COUNT(l) DESC")
    Page<Post> findByUserIdOrderByLikesCountDesc(@Param("userId") Long userId, Pageable pageable);
}

