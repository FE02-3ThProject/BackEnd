package com.github.gather.dto.response;

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

    public UserInfoResponse(Long userId, String nickname, String email, String image, Location location , Category category) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.image = image;
        this.locationId = location;
        this.categoryId = category;
    }

}
