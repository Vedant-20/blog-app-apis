package com.vedant.blog.services;

import java.util.List;

import com.vedant.blog.payloads.CommentDto;

public interface CommentService {

    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);

    public CommentDto updateComment(CommentDto commentDto, Integer commentId);

    public void deleteComment(Integer commentId);

    public List<CommentDto> getCommentsByPost(Integer postId);

}
