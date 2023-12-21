package com.github.gather.dto.response.user;

import com.github.gather.entity.Category;
import com.github.gather.entity.Location;
import lombok.*;

@Getter
@Setter
public class UserInfoResponse {

    private Long userId;
    private String nickname;
    private String email;
    private String image;
    private Location locationId;
    private Category categoryId;
    private String introduction;

    public UserInfoResponse(Long userId, String nickname, String email, String image, Location location , Category category,String introduction) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.image = image;
        this.locationId = location;
        this.categoryId = category;
        this.introduction = introduction;
    }

}
