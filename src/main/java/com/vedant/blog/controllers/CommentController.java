package com.vedant.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedant.blog.payloads.ApiResponse;
import com.vedant.blog.payloads.CommentDto;
import com.vedant.blog.services.CommentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId,
            @PathVariable Integer userId) {
        CommentDto createdComment = this.commentService.createComment(commentDto, postId, userId);
        return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,
            @PathVariable Integer commentId) {
        CommentDto updatedComment = this.commentService.updateComment(commentDto, commentId);
        return new ResponseEntity<CommentDto>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Integer postId) {
        List<CommentDto> comments = this.commentService.getCommentsByPost(postId);
        return new ResponseEntity<List<CommentDto>>(comments, HttpStatus.OK);
    }

}
