package com.github.gather.service;

import com.github.gather.dto.response.GroupCategoryResponse;
import com.github.gather.entity.Category;
import com.github.gather.exception.CategoryNotFoundException;
import com.github.gather.repositroy.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;

    public GroupCategoryResponse getCategoryInfo(Long categoryId) {
        Category category = userCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리가 없습니다."));

        // Convert Category entity to GroupCategoryResponse and return
        return new GroupCategoryResponse(category.getCategoryId(), category.getCategoryName());
    }
}

    // 카테고리별 모임 조회
//    public Long getCategoryId(Long categoryId){
//        Category category = getCategoryById(categoryId);
//        return category.getCategoryId();
//    }
//
//    public String getCategoryName(Long categoryId) {
//        Category category = getCategoryById(categoryId);
//        return category.getName();


    // Search for the category corresponding to a specific ID
//    private Category getCategoryById(Long categoryId) {
//        return UserCategoryRepository.(() -> new CategoryNotFoundException("Category not found. ID: " + categoryId));
//    }


//    // 카테고리별 모임 조회
//    public GroupCategoryResponse getCategoryInfo(Long categoryId) {
//        Category category = getCategoryById(categoryId);
//
//        // GroupCategoryResponse 객체를 생성하여 필요한 정보를 설정
//        GroupCategoryResponse response = new GroupCategoryResponse(
//        response.setGroupId(categoryId);
//        response.setLocationName(category.getName()); // 카테고리 이름을 위치명으로 설정
//        response.setCategoryName(category.getName()); // 카테고리 이름을 카테고리명으로 설정
//        response.setTitle(""); // 타이틀은 빈 값으로 설정 또는 카테고리에 따라 설정
//        response.setDescription(""); // 설명은 빈 값으로 설정 또는 카테고리에 따라 설정
//        response.setImage(""); // 이미지는 빈 값으로 설정 또는 카테고리에 따라 설정
//        response.setMaxMembers(0); // 최대 멤버 수는 0 또는 카테고리에 따라 설정
//        response.setCreatedAt(null); // 생성일은 빈 값으로 설정 또는 카테고리에 따라 설정
//
//        return response;
//    }

//    // 새로운 카테고리 생성
//    public Category createCategory(GroupCategoryRequest request) {
//        Category category = new Category();
//        category.setName(request.getName());
//        return userCategoryRepository.save(category);
//    }
//
//    // 기존의 카테고리 수정
//    public Category updateCategory(Long categoryId, Category updatedCategory) {
//        Category existingCategory = getCategoryById(categoryId);
//        existingCategory.setName(updatedCategory.getName());
//        // 필요한 다른 필드들도 업데이트할 수 있음
//        return userCategoryRepository.save(existingCategory);
//    }

//    // 특정 카테고리 삭제
//    public void deleteCategory(Long categoryId) {
//        userCategoryRepository.deleteById(categoryId);
//    }




