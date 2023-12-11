package com.github.gather.dto.response;

import com.github.gather.entity.Location;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditResponse {

    private Long userId;
    private String nickname;
    private String email;
    private String password;
    private String phoneNumber;
    private String image;
    private Location location;

}
