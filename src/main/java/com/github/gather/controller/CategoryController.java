package com.github.gather.controller;

import com.github.gather.dto.request.GroupCategoryRequest;
import com.github.gather.dto.response.UserInfoResponse;
import com.github.gather.entity.Category;
import com.github.gather.service.UserCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group/category")
public class CategoryController {


    private final UserCategoryService categoryService;
    private final UserCategoryService userCategoryService;

    public CategoryController(UserCategoryService categoryService, UserCategoryService userCategoryService){
        this.categoryService = categoryService;
        this.userCategoryService = userCategoryService;
    }

    // 모든 카테고리 목록 조회
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // 카테고리별 모임 조회
    @GetMapping("/{categoryId}")
    public ResponseEntity<UserInfoResponse> getCategoryById(@PathVariable Long categoryId) {
        UserInfoResponse userInfo = userCategoryService.getCategoryInfo(categoryId);
        return ResponseEntity.ok(userInfo);
    }



    // 새로운 카테고리 생성
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody GroupCategoryRequest request) {
        Category createdCategory = categoryService.createCategory(request);
        return ResponseEntity.ok(createdCategory);
    }

    // 카테고리 수정
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category updatedCategory) {
        Category existingCategory = categoryService.updateCategory(categoryId, updatedCategory);
        return ResponseEntity.ok(existingCategory);
    }

    // 특정 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("카테고리가 되었습니다.");
    }
}

