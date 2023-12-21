package com.github.gather.dto.request.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupRequest {

    private Long categoryId; // 1~10
    private Long locationId; // 1~17 , 도 단위.
    private String title;
    private String description;
//    private String image;
    private Integer maxMembers;
    //private LocalDate createdAt;


}



