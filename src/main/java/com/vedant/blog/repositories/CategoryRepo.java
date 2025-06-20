package com.vedant.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedant.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
