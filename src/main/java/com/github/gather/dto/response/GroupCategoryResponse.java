package com.github.gather.dto.response;

public class GroupCategoryResponse {

    private Long categoryId;
    private String categoryName;  // Add this line

    // modified constructor
    public GroupCategoryResponse(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;  // Add this line
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {  // Add this method
        return categoryName;
    }

    public void setCategoryName(String categoryName) {  // Add this method
        this.categoryName = categoryName;
    }
}

