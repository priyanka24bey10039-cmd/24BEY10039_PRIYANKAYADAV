package com.library.service.impl;

import com.library.dto.request.CategoryRequest;
import com.library.dto.response.CategoryResponse;
import com.library.exception.DuplicateResourceException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Category;
import com.library.repository.CategoryRepository;
import com.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        log.info("[CategoryService] Creating category: {}", request.getCategoryName());
        if (categoryRepository.existsByCategoryNameIgnoreCase(request.getCategoryName())) {
            throw new DuplicateResourceException("Category", "name", request.getCategoryName());
        }
        Category category = Category.builder()
                .categoryName(request.getCategoryName())
                .description(request.getDescription())
                .build();
        Category saved = categoryRepository.save(category);
        log.info("[CategoryService] Category created: {}", saved.getCategoryId());
        return toResponse(saved);
    }

    @Override
    public CategoryResponse getCategoryById(String categoryId) {
        log.info("[CategoryService] Fetching category: {}", categoryId);
        return toResponse(findById(categoryId));
    }

    @Override
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        log.info("[CategoryService] Fetching all categories");
        return categoryRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public CategoryResponse updateCategory(String categoryId, CategoryRequest request) {
        log.info("[CategoryService] Updating category: {}", categoryId);
        Category category = findById(categoryId);
        if (!category.getCategoryName().equalsIgnoreCase(request.getCategoryName())
                && categoryRepository.existsByCategoryNameIgnoreCase(request.getCategoryName())) {
            throw new DuplicateResourceException("Category", "name", request.getCategoryName());
        }
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        return toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(String categoryId) {
        log.info("[CategoryService] Deleting category: {}", categoryId);
        categoryRepository.delete(findById(categoryId));
        log.info("[CategoryService] Category deleted: {}", categoryId);
    }

    private Category findById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    private CategoryResponse toResponse(Category c) {
        return CategoryResponse.builder()
                .categoryId(c.getCategoryId())
                .categoryName(c.getCategoryName())
                .description(c.getDescription())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build();
    }
}
