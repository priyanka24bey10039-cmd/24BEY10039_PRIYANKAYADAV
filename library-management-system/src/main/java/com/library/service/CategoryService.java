package com.library.service;

import com.library.dto.request.CategoryRequest;
import com.library.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategoryById(String categoryId);
    Page<CategoryResponse> getAllCategories(Pageable pageable);
    CategoryResponse updateCategory(String categoryId, CategoryRequest request);
    void deleteCategory(String categoryId);
}
