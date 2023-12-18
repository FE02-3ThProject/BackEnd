package com.github.gather.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GroupCategoryResponse {
    private Long groupId;
    private String locationName;
    private String categoryName;
    private String title;
    private String description;
    private String image;
    private Integer maxMembers;
    private LocalDateTime createdAt;

    public GroupCategoryResponse(Long groupId, String locationName) {
        this.groupId = groupId;
        this.locationName = locationName;
        this.categoryName = categoryName;
        this.title = title;
        this.description = description;
        this.image = image;
        this.maxMembers = maxMembers;
        this.createdAt = createdAt;
    }
}

