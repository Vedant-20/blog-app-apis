package com.vedant.blog.payloads;

import java.util.Date;

import com.vedant.blog.entities.Category;
import com.vedant.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;

	private String postTitle;
	
	private String content;
	
	private String image;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	
}
