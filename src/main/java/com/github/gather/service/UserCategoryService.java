package com.github.gather.service;

import com.github.gather.dto.request.GroupCategoryRequest;
import com.github.gather.dto.response.UserInfoResponse;
import com.github.gather.entity.Category;
import com.github.gather.exception.CategoryNotFoundException;
import com.github.gather.repositroy.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
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

    // 카테고리별 모임 조회
    public UserInfoResponse getCategoryInfo(Long categoryId) {
        Category category = getCategoryById(categoryId);
        // UserInfoResponse 객체를 생성하여 필요한 정보를 설정
        UserInfoResponse userInfo = new UserInfoResponse();
        userInfo.setUserId(categoryId);
        userInfo.setNickname(category.getName()); // 카테고리 이름을 닉네임으로 설정
        userInfo.setEmail(""); // 이메일은 빈 값으로 설정 또는 카테고리에 따라 설정
        userInfo.setPhoneNumber(""); // 전화번호는 빈 값으로 설정 또는 카테고리에 따라 설정
        userInfo.setImage(""); // 이미지는 빈 값으로 설정 또는 카테고리에 따라 설정
        userInfo.setLocationId(categoryId); // 카테고리 ID를 위치 ID로 설정
        userInfo.setCategoryId(categoryId); // 카테고리 ID를 카테고리 ID로 설정
        userInfo.setAboutMe(""); // 자기 소개는 빈 값으로 설정 또는 카테고리에 따라 설정
        return userInfo;
    }

    // 새로운 카테고리 생성
    public Category createCategory(GroupCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
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

    // 특정 ID에 해당하는 카테고리 조회
    private Category getCategoryById(Long categoryId) {
        return userCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다. ID: " + categoryId));
    }
}


