package com.github.gather.dto.response;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class UserInfoResponse {

    private Long userId;
    private String nickname;
    private String email;
    private String image;
    private LocationResponse location;
    private GroupCategoryResponse category;
    private IntroductionResponse introduction;


    }



