package com.vedant.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vedant.blog.entities.Category;
import com.vedant.blog.entities.Post;
import com.vedant.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);

	@Query("SELECT p FROM Post p WHERE p.postTitle LIKE :key")
	List<Post> searchByTitle(@Param("key") String keyword);

}
