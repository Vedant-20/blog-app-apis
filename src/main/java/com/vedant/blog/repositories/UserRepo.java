package com.vedant.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedant.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
