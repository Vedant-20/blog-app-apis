package com.vedant.blog.services.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedant.blog.entities.Comment;
import com.vedant.blog.exceptions.ResourceNotFoundException;
import com.vedant.blog.payloads.CommentDto;
import com.vedant.blog.repositories.CommentRepo;
import com.vedant.blog.repositories.PostRepo;
import com.vedant.blog.repositories.UserRepo;
import com.vedant.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		// Check if the post exists
		if (!this.postRepo.existsById(postId)) {
			throw new ResourceNotFoundException("Post", "Post ID", postId);
		}

		// Map CommentDto to Comment entity
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId)));
		comment.setUser(this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId)));
		comment.setAddedDate(new Date());
		// Save the comment
		Comment savedComment = this.commentRepo.save(comment);
		// Map saved Comment entity back to CommentDto
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {

		// Check if the comment exists
		Comment existingComment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment ID", commentId));

		// Update the content of the existing comment
		existingComment.setContent(commentDto.getContent());
		existingComment.setAddedDate(new Date());

		// Save the updated comment
		Comment updatedComment = this.commentRepo.save(existingComment);

		// Map updated Comment entity back to CommentDto
		return this.modelMapper.map(updatedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// Check if the comment exists
		Comment existingComment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment ID", commentId));

		// Delete the comment
		this.commentRepo.delete(existingComment);

	}

	@Override
	public List<CommentDto> getCommentsByPost(Integer postId) {
		// Check if the post exists
		if (!this.postRepo.existsById(postId)) {
			throw new ResourceNotFoundException("Post", "Post ID", postId);
		}

		// Fetch comments for the post
		List<Comment> comments = this.commentRepo.findByPostId(postId);

		// Map List<Comment> to List<CommentDto>
		return comments.stream()
				.map(comment -> this.modelMapper.map(comment, CommentDto.class))
				.toList();
	}

}
