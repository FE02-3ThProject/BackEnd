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
public class UpdatedGroupInfoResponse {
    private Long groupId;
    private Long categoryId;
    private Long locationId;
    private String title;
    private String description;
    private String image;
    private LocalDate createdAt;
    private Integer maxMembers;
}
