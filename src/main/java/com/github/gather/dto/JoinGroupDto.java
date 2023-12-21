package com.github.gather.dto;

import com.github.gather.entity.Category;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinGroupDto {

    private Long groupId;
    private Location locationId;
    private String title;
    private String image;
    private String description;
    private Integer maxMembers;
    private Category categoryId;
    private LocalDate createAt;

    public JoinGroupDto(GroupTable groupTable){

        this.groupId = groupTable.getGroupId();
        this.locationId = groupTable.getLocationId();
        this.title = groupTable.getTitle();
        this.image = groupTable.getImage();
        this.description = groupTable.getDescription();
        this.maxMembers = groupTable.getMaxMembers();
        this.categoryId = groupTable.getCategoryId();
        this.createAt = groupTable.getCreatedAt();
    }
}
