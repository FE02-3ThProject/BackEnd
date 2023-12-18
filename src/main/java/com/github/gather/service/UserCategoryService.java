package com.github.gather.service;

import com.github.gather.entity.Category;
import com.github.gather.exception.CategoryNotFoundException;
import com.github.gather.repositroy.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;

    @Autowired
    public UserCategoryService(UserCategoryRepository userCategoryRepository) {
        this.userCategoryRepository = userCategoryRepository;
    }

    // 모든 카테고리 목록 조회
    public List<Category> getAllCategories() {
        return userCategoryRepository.findAll();
    }

    // 특정 카테고리 조회
    public Category getCategoryById(Long categoryId) {
        return userCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다. ID: " + categoryId));
    }

    // 새로운 카테고리 생성
    public Category createCategory(Category category) {
        return userCategoryRepository.save(category);
    }

    // 기존의 카테고리 수정
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(updatedCategory.getName());
        // 필요한 다른 필드들도 업데이트할 수 있음

        return userCategoryRepository.save(existingCategory);
    }

    // 특정 카테고리 삭제
    public void deleteCategory(Long categoryId) {
        userCategoryRepository.deleteById(categoryId);
    }
}