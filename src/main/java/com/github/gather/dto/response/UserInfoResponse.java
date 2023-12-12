package com.github.gather.dto.response;

import com.github.gather.entity.Location;
import lombok.*;

@Getter
@Setter
public class UserInfoResponse {

    private Long userId;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String image;
    private Location locationId;

    public UserInfoResponse(Long userId, String nickname, String email, String phoneNumber, String image, Location location) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.locationId = location;
    }

}
