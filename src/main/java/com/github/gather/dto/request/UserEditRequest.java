package com.github.gather.dto.request;


import com.github.gather.entity.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditRequest {

    private Long userId;
    private String nickname;
    private String email;
    private String password;
    private String phoneNumber;
    private String image;
    private Location location;

}
