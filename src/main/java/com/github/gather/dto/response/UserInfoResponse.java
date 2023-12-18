package com.github.gather.dto.response;

import lombok.*;


@Getter
@Setter
public class UserInfoResponse {

    private Long userId;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String image;
    private LocationResponse location;
    private GroupCategoryResponse category;
    private IntroductionResponse introduction;

    public UserInfoResponse(Long userId, String nickname, String email, String phoneNumber, String image, LocationResponse location, GroupCategoryResponse category, IntroductionResponse introduction) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.location = location;
        this.category = category;
        this.introduction = introduction;
    }

    public UserInfoResponse() {
        
    }

    public void setAboutMe(String s) {
    }

    public void setCategoryId(Long categoryId) {
    }

    public void setLocationId(Long categoryId) {

    }
}


