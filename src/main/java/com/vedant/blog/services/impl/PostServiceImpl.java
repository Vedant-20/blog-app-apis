package com.vedant.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vedant.blog.entities.Category;
import com.vedant.blog.entities.Post;
import com.vedant.blog.entities.User;
import com.vedant.blog.exceptions.ResourceNotFoundException;
import com.vedant.blog.payloads.PostDto;
import com.vedant.blog.payloads.PostResponse;
import com.vedant.blog.repositories.CategoryRepo;
import com.vedant.blog.repositories.PostRepo;
import com.vedant.blog.repositories.UserRepo;
import com.vedant.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImage(postDto.getImage() == null ? "default.png" : postDto.getImage());
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		post.setPostTitle(postDto.getPostTitle());
		post.setContent(postDto.getContent());
		post.setImage(postDto.getImage());
		Post updatedPost = this.postRepo.save(post);

		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {

		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		Page<Post> pagePost = this.postRepo.findAll(p);

		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));

		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;

	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		if (keyword != null && !keyword.isEmpty()) {
			List<Post> posts = this.postRepo.searchByTitle("%" + keyword + "%");
			List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
					.collect(Collectors.toList());
			return postDtos;
		}

		return null;
	}

}
