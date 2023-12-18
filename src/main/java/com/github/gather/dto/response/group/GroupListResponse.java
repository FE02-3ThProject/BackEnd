package com.github.gather.dto.response.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupListResponse {

    private Long groupId;
    private String locationName;
    private String categoryName;
    private String title;
    private String description;
    private String image;
    private Integer maxMembers;
    private LocalDate createdAt;
    private Long joinedGroupMembers;

}
