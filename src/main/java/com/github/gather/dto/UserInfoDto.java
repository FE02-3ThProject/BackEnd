package com.github.gather.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private String nickname;
    private Long locationId;
    private Long categoryId;
    private String image;
    private String introduction;

}

