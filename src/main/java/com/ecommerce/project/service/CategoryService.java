package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;


public interface CategoryService {
    CategoryResponse getAllCategories();
    CategoryResponse createCategory(CategoryDTO categoryDTO);
    String deleteCategory(Long CategoryId);
    void updateCategory(Category category, Long categoryId);
}