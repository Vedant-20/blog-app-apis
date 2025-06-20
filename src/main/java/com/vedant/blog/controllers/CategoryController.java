package com.vedant.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedant.blog.payloads.ApiResponse;
import com.vedant.blog.payloads.CategoryDto;
import com.vedant.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/createCategory")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto createdCategory=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategory,HttpStatus.CREATED);
	}
	
	@PutMapping("/updateCategory/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		CategoryDto updatedCategory=this.categoryService.updateCategory(categoryDto,categoryId);
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory( @PathVariable Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully !!",true),HttpStatus.OK);
	}
	
	@GetMapping("/getCategoryById/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory( @PathVariable Integer categoryId){
		CategoryDto category=this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(category,HttpStatus.OK);
	}
	
	
	@GetMapping("/getAllCategories")
	public ResponseEntity<List<CategoryDto>> getCategories(){
		List<CategoryDto> categories=this.categoryService.getCategories();
		return new ResponseEntity<List<CategoryDto>>(categories,HttpStatus.OK);
	}
}
