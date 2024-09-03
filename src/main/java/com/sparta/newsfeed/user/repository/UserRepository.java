package com.sparta.newsfeed.user.repository;

import com.sparta.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    boolean existsByEmail(String email);
}

