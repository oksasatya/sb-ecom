package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new APIException("no Categories created till now!!");
        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setMessage("Category successfully retrieved!");
        return categoryResponse;
    }

    @Override
    public CategoryResponse createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDTO = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDTO != null) {
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists");
        }
        Category createdCategory = categoryRepository.save(category);

        // response APi
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(List.of(modelMapper.map(createdCategory, CategoryDTO.class)));
        categoryResponse.setMessage("Category created successfully!");
        return categoryResponse;
    }


    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
        categoryRepository.delete(category);
        return "Category with id " + categoryId + " was deleted Successfully";
    }

    @Override
    public void updateCategory(Category category, Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));

        category.setCategoryId(categoryId);
        categoryRepository.save(category);
    }
}
