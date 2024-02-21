package com.sparta.homeworktestcode.post;

import com.sparta.homeworktestcode.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User targetUser);
}
