package com.github.gather.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupCategoryRequest {

    private Long groupId;
    private String locationName;
    private String categoryName;
    private String title;
    private String description;
    private String image;
    private Integer maxMembers;
    private String createdAt;

    public GroupCategoryRequest(Long groupId, String locationName, String categoryName, String title, String description, String image, Integer maxMembers, String createdAt) {
        this.groupId = groupId;
        this.locationName = locationName;
        this.categoryName = categoryName;
        this.title = title;
        this.description = description;
        this.image = image;
        this.maxMembers = maxMembers;
        this.createdAt = createdAt;
    }

    public String getName() {
        return this.categoryName;
    }
}
