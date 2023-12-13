package com.github.gather.dto.request.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGroupInfoRequest {

    private Long categoryId; // 1~10 //category 변경가능
    private Long locationId; // 1~17 , 도 단위. //location 변경가능
    private String title;
    private String description;
    private String image;
    private Integer maxMembers;



}
