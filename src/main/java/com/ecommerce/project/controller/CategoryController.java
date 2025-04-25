package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/public/categories",method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllCategories() {
        CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @RequestMapping(value ="/admin/categories" ,method = RequestMethod.POST)
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(categoryResponse,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/admin/categories/{categoryId}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        String status = categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @RequestMapping(value = "/admin/categories/{categoryId}",method = RequestMethod.PUT)
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category,@PathVariable Long categoryId) {
        categoryService.updateCategory(category,categoryId);
        return new ResponseEntity<>("Category updated successfully" + category.getCategoryName() ,HttpStatus.OK);
    }
}
