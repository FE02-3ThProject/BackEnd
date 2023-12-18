package com.github.gather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupCategoryResponse {
    private Long groupId;
    private String categoryName;

    public GroupCategoryResponse(Long categoryId) {
    }

    public Long getCategoryId() {
        return null;
    }

    public String getName() {
        return null;
    }
}

