package com.vedant.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedant.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

    List<Comment> findByPostId(Integer postId);

}
